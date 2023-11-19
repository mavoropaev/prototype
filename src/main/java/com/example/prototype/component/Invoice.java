package com.example.prototype.component;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

@Data
//@ConfigurationProperties(prefix = "invoice")
public class Invoice {

    private Environment env = new StandardEnvironment();

    @Value("${invoice.eshopId}")
    private String eshopId;
    private String orderId;
    private String recipientCurrency;
    private String secretKey;



    //@Value("${invoice.eshopId}")
   // @Value("${invoice.orderId}")
    //@Value("${invoice.recipientCurrency}")
   // @Value("${invoice.secretKey}")


    public Invoice(){
        this.eshopId = env.getProperty("invoice.eshopId");
        this.orderId = env.getProperty("invoice.orderId");
        this.recipientCurrency = env.getProperty("invoice.recipientCurrency");
        this.secretKey = env.getProperty("invoice.secretKey");

    }


}
