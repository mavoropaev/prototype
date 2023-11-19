package com.example.prototype.services;

import com.example.prototype.config.InvoiceProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    public static final int MAX_ORDER_NUM = 1000000;

    private final InvoiceProperties invoiceProperties;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @SneakyThrows
    public String md5Hex(String input) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String createInvoice(String recipientAmount, String email) {
        log.info("Создание счета на оплату для суммы {} и email {}", recipientAmount, email);

        String orderId = String.valueOf(new Random().nextInt(MAX_ORDER_NUM));

        String param5Hex = String.join("::", invoiceProperties.getEshopId(), orderId, "", recipientAmount,
                invoiceProperties.getRecipientCurrency(), "", email, "", "", "", "", "", "", "", invoiceProperties.getSecretKey());

        log.info("Хэш-сумма запроса {}", param5Hex);

        String hash = md5Hex(param5Hex);

        Map<String, String> params = new HashMap<>();
        params.put("eshopId", invoiceProperties.getEshopId());
        params.put("orderId", orderId);
        params.put("recipientAmount", recipientAmount);
        params.put("recipientCurrency", invoiceProperties.getRecipientCurrency());
        params.put("email", email);
        params.put("hash", hash);

        log.info("Отправка запроса на создание счета с параметрами: {}", params);

        try {
            HttpEntity<Map<String, String>> request = new HttpEntity<>(params);
            String response = restTemplate.postForObject(invoiceProperties.getInvoiceUrl(), request, String.class);
            String invoiceId = extractInvoiceIdFromJson(response);

            log.info("Счет на оплату успешно создан с ID: {}", invoiceId);
            return buildPaymentUrl(invoiceId);
        } catch (Exception e) {
            log.error("Ошибка при создании счета на оплату", e);
            throw e;
        }
    }


    public String buildPaymentUrl(String invoiceId) {
        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", String.format(invoiceProperties.getPaymentUrlTemplate(), invoiceId));
        try {
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            log.error("Ошибка при создании JSON строки: ", e);
            return null;
        }
    }

    private String extractInvoiceIdFromJson(String jsonStr) {
        try {
            Map<String, Object> result = objectMapper.readValue(jsonStr, Map.class);
            Map<String, Object> resultData = (Map<String, Object>) result.get("Result");
            return resultData.get("InvoiceId").toString();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }
}