package com.example.prototype.dto;

import lombok.Data;

@Data
public class PaymentNotificationBody {

    private String eshopId;
    private String paymentId;
    private String orderId;
    private String eshopAccount;
    private String serviceName;
    private String recipientOriginalAmount;
    private String recipientAmount;
    private String refundAmount;
    private String recipientCurrency;
    private String paymentStatus;
    private String userName;
    private String userEmail;
    private String paymentData;
    private String payMethod;
    private String shortPan;
    private String country;
    private String bank;
    private String ipAddress;
    private String secretKey;
    private String hash;

}
