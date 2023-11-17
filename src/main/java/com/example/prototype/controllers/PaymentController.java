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

    PaymentGate paymentGate = new PaymentGate();

    @GetMapping("1")
    public String requestCreateInvoice() throws NoSuchAlgorithmException {
        return paymentGate.createInvoice();
        //return "Test - worked";
    }

    @GetMapping("2")
    public String requestBankCardPayment() throws NoSuchAlgorithmException {
        return paymentGate.bankCardPayment();
        //return "Test - worked";
    }


}
