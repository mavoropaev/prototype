package com.example.prototype.controllers;

import com.example.prototype.dto.InvoiceBody;
import com.example.prototype.dto.PaymentNotificationBody;
import com.example.prototype.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/api/getNotified")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getNotified(PaymentNotificationBody paymentNotificationBody) {
        try {
            getNotificationStatus(paymentNotificationBody);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            log.error("Ошибка при получение уведомления: ", e);
            return ResponseEntity.status(500).body("Внутренняя ошибка сервера");
        }
    }

    private static void getNotificationStatus(PaymentNotificationBody paymentNotificationBody) {
        switch (paymentNotificationBody.getPaymentStatus()){
            case "3":{
                log.info("Создан счет к оплате (СКО) за покупку, paymentStatus: {}", paymentNotificationBody.getPaymentStatus());
                break;
            }
            case "4":{
                log.info("(СКО аннулирован, деньги возвращены пользователю, paymentStatus: {}", paymentNotificationBody.getPaymentStatus());
                break;
            }
            case "5":{
                log.info("СКО полностью оплачен, деньги переведены на счет интернет-магазина, paymentStatus: {}", paymentNotificationBody.getPaymentStatus());
                break;
            }
            case "6":{
                log.info("(Необходимая сумма заблокирована (холдирована) на СКО, ожидается запрос на списание или разблокировку средств или истечение срока блокировки, paymentStatus: {}", paymentNotificationBody.getPaymentStatus());
                break;
            }
            case "8":{
                log.info("По СКО был произведен возврат, paymentStatus: {}", paymentNotificationBody.getPaymentStatus());
                log.info("Сумма возврата, refundAmount: {}", paymentNotificationBody.getRefundAmount());
                log.info("Сумма, оставшаяся у интернет-магазина, recipientAmount: {}", paymentNotificationBody.getRecipientAmount());
                break;
            }
            default:{
                log.info("Неопределённый статус, paymentStatus: {}", paymentNotificationBody.getPaymentStatus());
            }
        }
    }

}