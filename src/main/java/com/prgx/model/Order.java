package com.prgx.model;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private List<Coffee> coffees;
    private List<Cookie> cookies;

}