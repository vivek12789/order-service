package com.prgx.model;

import lombok.Data;

@Data
public class OrderResponse {
    private String message;
    private double totalAmount;

    public OrderResponse(String message, double totalAmount) {
        this.message = message;
        this.totalAmount = totalAmount;
    }

}
