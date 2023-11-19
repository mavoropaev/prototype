package com.example.prototype.services;


import org.json.JSONObject;
import java.security.NoSuchAlgorithmException;

public class PaymentGate {

    PaymentService paymentService = new PaymentService();

    public String createInvoice() throws NoSuchAlgorithmException {

        String eshopId = "466418";
        int orderId = 1;
        String recipientAmount = "10.00";
        String recipientCurrency = "RUB";
        String email = "test@test.ru";
        String secretKey = "tmpKey";

       //String invoiceTxt = paymentService.createInvoice(eshopId, orderId, recipientAmount, recipientCurrency, email, secretKey);
       // String invoiceId = jsonRead(invoiceTxt);
       // return "InvoiceId::" + invoiceId;

        return "1";

        //return paymentService.createInvoice(eshopId, orderId, recipientAmount, recipientCurrency, email, secretKey);
    }

    private  String jsonRead(String jsonStr){

        JSONObject jsonObject = new JSONObject(jsonStr.toString());
        JSONObject resultJson = jsonObject.getJSONObject("Result");

        return  resultJson.get("InvoiceId").toString();

    }


}
