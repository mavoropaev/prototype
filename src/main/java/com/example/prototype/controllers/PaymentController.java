package com.example.prototype.controllers;

import com.example.prototype.dto.InvoiceBody;
import com.example.prototype.services.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;


@Controller
public class PaymentController {

    @GetMapping("/")
    public String indexStart() {
        return "index";
    }

    PaymentService paymentService = new PaymentService();

    @PostMapping("/api/createInvoice")
    @ResponseBody
    public String requestCreateInvoice(@RequestBody InvoiceBody invoiceBody) throws NoSuchAlgorithmException {

        double recipientAmount = Double.parseDouble(invoiceBody.getPrice()) * Double.parseDouble(invoiceBody.getQuantity());

        return paymentService.createInvoice(Double.toString(recipientAmount), invoiceBody.getEmail());

    }


}
