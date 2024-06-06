package com.prgx.model;

import lombok.Data;

@Data
public class ItemDetail {
    private String itemName;
    private double itemCost;

    public ItemDetail(String itemName, double itemCost) {
        this.itemName = itemName;
        this.itemCost = itemCost;
    }
}