package com.prgx.model;

import lombok.Data;

import java.util.List;

@Data
public class DetailedBillResponse {
    private List<ItemDetail> items;
    private double totalAmount;

    public DetailedBillResponse(List<ItemDetail> items, double totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }
}