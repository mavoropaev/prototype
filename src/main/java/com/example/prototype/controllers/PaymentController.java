package com.example.prototype.controllers;

import com.example.prototype.dto.InvoiceBody;
import com.example.prototype.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {


    private final PaymentService paymentService;

    @PostMapping("/api/createInvoice")
    public ResponseEntity<String> requestCreateInvoice(@RequestBody InvoiceBody invoiceBody) {
        try {
            log.info("Запрос на создание счета: количество={}, цена={}, email={}", invoiceBody.getQuantity(), invoiceBody.getPrice(), invoiceBody.getEmail());

            double recipientAmount = Double.parseDouble(invoiceBody.getPrice()) * Double.parseDouble(invoiceBody.getQuantity());
            String response = paymentService.createInvoice(Double.toString(recipientAmount), invoiceBody.getEmail());

            log.info("Счет успешно создан: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Ошибка при создании счета: ", e);
            return ResponseEntity.status(500).body("Внутренняя ошибка сервера");
        }
    }
}