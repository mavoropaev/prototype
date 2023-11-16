package com.example.prototype.services;


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

        return paymentService.createInvoice(eshopId, orderId, recipientAmount, recipientCurrency, email, secretKey);
    }

    public String bankCardPayment() throws NoSuchAlgorithmException {

        String eshopId = "466418";
        String secretKey = "tmpKey";
        String invoiceId = "3885065158";
        String pan = "1111222233334444";
        String cardHolder = "MIKHAIL VOROPAEV";
        String expiredMonth = "12";
        String expiredYear = "25";
        String cvv = "428";
        String returnUrl = "https://example.com/result.php";
        String ipAddress = "213.171.63.4";

        return paymentService.bankCardPayment(eshopId, invoiceId, pan, cardHolder, expiredMonth, expiredYear, cvv,
                returnUrl, ipAddress, secretKey);
    }


}
