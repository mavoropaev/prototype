package com.example.prototype.services;

import com.example.prototype.component.Invoice;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
//import javax.xml.parsers;
//import org.xml.sax;

@Service
@ConfigurationProperties(prefix = "invoice")
public class PaymentService {

    @Autowired
    private Environment env;
    //@Value("${invoice.eshopId}")
    private String eshopId;
    //@Value("${invoice.orderId}")
    private String orderId;
    //@Value("${invoice.recipientCurrency}")
    private String recipientCurrency;
    //@Value("${invoice.secretKey}")
    private String secretKey;


    public String md5Hex(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        //byte[] digest = md.digest(input.getBytes());
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String createInvoice(String recipientAmount, String email) throws NoSuchAlgorithmException {

        eshopId = "466418";
        orderId = "7";
        recipientCurrency = "RUB";
        secretKey = "tmpKey";

        String param5Hex = eshopId + "::" + orderId + "::" + "" + "::"
                + recipientAmount + "::" + recipientCurrency + "::" + "" + "::"
                + email + "::" + ""  + "::" + "" + "::" + "" + "::"
                + "" + "::" + "" + "::" + "" + "::" + ""  + "::" + secretKey;


        /*
        String param5Hex = invoice.getEshopId() + "::" + invoice.getOrderId() + "::" + "" + "::"
                + recipientAmount + "::" + invoice.getRecipientCurrency() + "::" + "" + "::"
                + email + "::" + ""  + "::" + "" + "::" + "" + "::"
                + "" + "::" + "" + "::" + "" + "::" + ""  + "::" + invoice.getSecretKey();

         */

        String hash = md5Hex(param5Hex);

        String url = "https://api.intellectmoney.ru/merchant/latest/createInvoice";

        Map<String, String> params = new HashMap<>();
        params.put("eshopId", eshopId);
        //params.put("eshopId", invoice.getEshopId());
        params.put("orderId", orderId);
        //params.put("orderId", String.valueOf(invoice.getOrderId()));
        params.put("recipientAmount", recipientAmount);
        params.put("recipientCurrency", recipientCurrency);
        //params.put("recipientCurrency", invoice.getRecipientCurrency());
        params.put("email", email);
        params.put("hash", hash);

        RestTemplate restTemplate = new RestTemplate();
        //HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(params);

        String invoiceIdStr =  jsonRead(Objects.requireNonNull(restTemplate.postForObject(url, request, String.class)));

        String jsonUrlPayment = jsonCreate(invoiceIdStr);
        return jsonUrlPayment;
    }

    private  String jsonRead(String jsonStr){

        JSONObject jsonObject = new JSONObject(jsonStr.toString());
        JSONObject resultJson = jsonObject.getJSONObject("Result");

        return  resultJson.get("InvoiceId").toString();
    }

    private String jsonCreate(String invoice){

        JSONObject jsonObject = new JSONObject();
        String paymentUrl = "https://merchant.intellectmoney.ru/?InvoiceId=" + invoice;
        jsonObject.put("paymentUrl", paymentUrl);

        return jsonObject.toString();
    }

}