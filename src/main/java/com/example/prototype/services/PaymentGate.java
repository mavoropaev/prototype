package com.example.prototype.services;


import java.security.NoSuchAlgorithmException;

public class PaymentGate {

    public String payment() throws NoSuchAlgorithmException {

        PaymentService paymentService = new PaymentService();

        String eshopId = "450123";
        int orderId = 1;
        String recipientAmount = "10.00";
        String recipientCurrency = "RUB";
        String email = "e@e.com";
        String secretKey = "Secret key";

        return paymentService.createInvoice(eshopId, orderId, recipientAmount, recipientCurrency, email, secretKey);

    }


}
