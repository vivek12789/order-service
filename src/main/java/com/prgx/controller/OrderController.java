package com.prgx.controller;

import com.prgx.model.DetailedBillResponse;
import com.prgx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.prgx.model.Order;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<DetailedBillResponse> createOrder(@RequestBody Order order) {
        DetailedBillResponse response = orderService.calculateTotal(order);
        log.info("Generated bill: {}", response);
        return ResponseEntity.ok(response);
    }
}
