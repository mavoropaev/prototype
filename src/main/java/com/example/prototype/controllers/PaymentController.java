package com.example.prototype.controllers;

import com.example.prototype.services.PaymentGate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class PaymentController {

    PaymentGate paymentGate;

    @GetMapping
    public String requestPayment() throws NoSuchAlgorithmException {
        return paymentGate.payment();
    }


}
