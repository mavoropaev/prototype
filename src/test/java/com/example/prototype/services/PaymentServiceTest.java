package com.example.prototype.services;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Test
    void md5Hex_CorrectInput_ReturnCorrectMd5Hash() throws NoSuchAlgorithmException {
        PaymentService paymentService = new PaymentService();

        String strInput = "testString";
        String expectedMd5Hash = "536788f4dbdffeecfbb8f350a941eea3";

        String generateMd5Hash = paymentService.md5Hex(strInput);

        assertEquals(expectedMd5Hash, generateMd5Hash, "MD5 hash должен соответствовать ожидаемому значению");

    }
}