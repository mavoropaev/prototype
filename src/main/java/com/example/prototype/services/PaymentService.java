package com.example.prototype.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
//import javax.xml.parsers;
//import org.xml.sax;

@Service
public class PaymentService {

    private String md5Hex(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String createInvoice(String eshopId, int orderId, String recipientAmount, String recipientCurrency, String email, String secretKey) throws NoSuchAlgorithmException {
        String hash = md5Hex(eshopId + "::" + orderId + "::" + "" /* serviceName */ + "::"
                                 + recipientAmount + "::" + recipientCurrency + "::" + "" /* userName */ + "::"
                                 + email + "::" + "" /* successUrl */ + "::" + "" /* failUrl */ + "::" + "" /* backUrl */ + "::"
                                 + "" /* resultUrl */ + "::" + "" /* expireDate */ + "::" + "" /* holdMode */ + "::" + "" /* preference */ + "::" + secretKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String url = "https://api.intellectmoney.ru/merchant/latest/createInvoice";

        Map<String, String> params = new HashMap<>();
        params.put("eshopId", eshopId);
        params.put("orderId", String.valueOf(orderId));
        params.put("recipientAmount", recipientAmount);
        params.put("recipientCurrency", recipientCurrency);
        params.put("email", email);
        params.put("hash", hash);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

        return restTemplate.postForObject(url, request, String.class);
    }


    public String bankCardPayment(String eshopId, String invoiceId, String pan, String cardHolder, String expiredMonth, String expiredYear, String cvv,
                                  String returnUrl, String ipAddress, String secretKey) throws NoSuchAlgorithmException {
        String hash = md5Hex(eshopId + "::" + invoiceId + "::" + pan + "::" + cardHolder + "::" + expiredMonth + "::" + expiredYear + "::" +
                                   cvv + "::" + returnUrl + "::" + ipAddress + "::" + secretKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String url = "https://api.intellectmoney.ru/merchant/bankcardpayment";

        Map<String, String> params = new HashMap<>();
        params.put("eshopId", eshopId);
        params.put("invoiceId", invoiceId);
        params.put("pan", pan);
        params.put("cardHolder", cardHolder);
        params.put("expiredMonth", expiredMonth);
        params.put("expiredYear", expiredYear);
        params.put("cvv", cvv);
        params.put("returnUrl", returnUrl);
        params.put("ipAddress", ipAddress);
        params.put("hash", hash);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

        return restTemplate.postForObject(url, request, String.class);
    }
}