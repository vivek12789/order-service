package com.prgx.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class Coffee {
    private final String name;
    private final int quantity;
    private final List<AddOn> addOns;

}
