package com.example.prototype.dto;

import lombok.Data;

@Data
public class InvoiceBody {
    private String price;
    private String quantity;
    private String email;
}
