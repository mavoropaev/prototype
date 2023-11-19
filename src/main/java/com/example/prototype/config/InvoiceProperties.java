package com.example.prototype.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "invoice")
@Configuration
public class InvoiceProperties {
    private String eshopId;
    private String recipientCurrency;
    private String secretKey;
    private String paymentUrlTemplate;
    private String invoiceUrl;
}
