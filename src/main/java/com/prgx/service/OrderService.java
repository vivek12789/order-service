package com.prgx.service;

import com.prgx.config.PricesConfig;
import com.prgx.model.AddOn;
import com.prgx.model.Coffee;
import com.prgx.model.Cookie;
import com.prgx.model.Order;
import com.prgx.model.DetailedBillResponse;
import com.prgx.model.ItemDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    private final PricesConfig pricesConfig;

    @Autowired
    public OrderService(PricesConfig pricesConfig) {
        this.pricesConfig = pricesConfig;
    }

    public DetailedBillResponse calculateTotal(Order order) {
        log.info("Calculating total for order: {}", order);
        double total = 0;
        List<ItemDetail> items = new ArrayList<>();

        for (Coffee coffee : order.getCoffees()) {
            double coffeeTotal = pricesConfig.getCoffee().getOrDefault(coffee.getName().toLowerCase(), 0.0);
            for (AddOn addOn : coffee.getAddOns()) {
                coffeeTotal += pricesConfig.getAddon().getOrDefault(addOn.getName().toLowerCase(), 0.0);
            }
            double coffeeCost = coffeeTotal * coffee.getQuantity();
            total += coffeeCost;
            items.add(new ItemDetail(coffee.getName() + " x " + coffee.getQuantity(), coffeeCost));
        }

        for (Cookie cookie : order.getCookies()) {
            double cookieCost = pricesConfig.getCookie().getOrDefault(cookie.getName().toLowerCase(), 0.0) * cookie.getQuantity();
            total += cookieCost;
            items.add(new ItemDetail(cookie.getName() + " x " + cookie.getQuantity(), cookieCost));
        }
        log.info("Total amount for order: {}", total);
        return new DetailedBillResponse(items, total);
    }
}
