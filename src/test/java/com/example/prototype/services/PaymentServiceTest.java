package com.example.prototype.services;

import com.example.prototype.config.InvoiceProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    InvoiceProperties invoiceProperties;

    @InjectMocks
    PaymentService paymentService;

    @Test
    void md5Hex_CorrectInput_ReturnCorrectMd5Hash() throws NoSuchAlgorithmException {
        String strInput = "testString";
        String expectedMd5Hash = "536788f4dbdffeecfbb8f350a941eea3";

        String generateMd5Hash = paymentService.md5Hex(strInput);

        assertEquals(expectedMd5Hash, generateMd5Hash, "MD5 hash должен соответствовать ожидаемому значению");
    }
}