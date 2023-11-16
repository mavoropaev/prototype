package com.example.prototype.services;


import java.security.NoSuchAlgorithmException;

public class PaymentGate {

    public String payment() throws NoSuchAlgorithmException {

        PaymentService paymentService = new PaymentService();

        String eshopId = "466418";
        int orderId = 1;
        String recipientAmount = "10.00";
        String recipientCurrency = "RUB";
        String email = "test@test.ru";
        String secretKey = "tmpKey";


        return paymentService.createInvoice(eshopId, orderId, recipientAmount, recipientCurrency, email, secretKey);

    }


}
