package com.prgx.controller;

import com.prgx.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setCoffees(Arrays.asList(
            new Coffee("Cappuccino", 2, Arrays.asList(new AddOn("ARABICA"), new AddOn("SOYA MILK"))),
            new Coffee("Latte", 1, Arrays.asList(new AddOn("ROBUSTA"), new AddOn("ALMOND MILK")))
        ));
        order.setCookies(Arrays.asList(
            new Cookie("Chocolate", 3),
            new Cookie("Oatmeal", 2)
        ));

        HttpEntity<Order> request = new HttpEntity<>(order);

        ResponseEntity<DetailedBillResponse> response = restTemplate.exchange(
            "/api/orders",
            HttpMethod.POST,
            request,
            DetailedBillResponse.class
        );

        assertEquals(200, response.getStatusCodeValue());

        DetailedBillResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(765.0, responseBody.getTotalAmount(), 0.01);
        assertEquals(4, responseBody.getItems().size());
    }

    @Test
    public void testOrderWithNoAddOns() {
        Order order = new Order();
        order.setCoffees(Arrays.asList(
            new Coffee("Espresso", 3, Collections.emptyList()),
            new Coffee("Latte", 1, Collections.emptyList())
        ));
        order.setCookies(Arrays.asList(
            new Cookie("Chocolate", 1)
        ));

        HttpEntity<Order> request = new HttpEntity<>(order);

        ResponseEntity<DetailedBillResponse> response = restTemplate.exchange(
            "/api/orders",
            HttpMethod.POST,
            request,
            DetailedBillResponse.class
        );

        assertEquals(200, response.getStatusCodeValue());

        DetailedBillResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(490.0, responseBody.getTotalAmount(), 0.01);
        assertEquals(3, responseBody.getItems().size());
    }

    @Test
    public void testOrderWithNoCookies() {
        Order order = new Order();
        order.setCoffees(Arrays.asList(
            new Coffee("Cappuccino", 1, Arrays.asList(new AddOn("EXCELSA"))),
            new Coffee("Espresso", 2, Arrays.asList(new AddOn("LIBERICA")))
        ));
        order.setCookies(Collections.emptyList());

        HttpEntity<Order> request = new HttpEntity<>(order);

        ResponseEntity<DetailedBillResponse> response = restTemplate.exchange(
            "/api/orders",
            HttpMethod.POST,
            request,
            DetailedBillResponse.class
        );

        assertEquals(200, response.getStatusCodeValue());

        DetailedBillResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(510.0, responseBody.getTotalAmount(), 0.01);
        assertEquals(2, responseBody.getItems().size());
    }

    @Test
    public void testEmptyOrder() {
        Order order = new Order();
        order.setCoffees(Collections.emptyList());
        order.setCookies(Collections.emptyList());

        HttpEntity<Order> request = new HttpEntity<>(order);

        ResponseEntity<DetailedBillResponse> response = restTemplate.exchange(
            "/api/orders",
            HttpMethod.POST,
            request,
            DetailedBillResponse.class
        );

        assertEquals(200, response.getStatusCodeValue());

        DetailedBillResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(0.0, responseBody.getTotalAmount(), 0.01);
        assertEquals(0, responseBody.getItems().size());
    }

    @Test
    public void testInvalidItemName() {
        Order order = new Order();
        order.setCoffees(Arrays.asList(
            new Coffee("InvalidCoffee", 1, Arrays.asList(new AddOn("InvalidAddOn")))
        ));
        order.setCookies(Arrays.asList(
            new Cookie("InvalidCookie", 1)
        ));

        HttpEntity<Order> request = new HttpEntity<>(order);

        ResponseEntity<DetailedBillResponse> response = restTemplate.exchange(
            "/api/orders",
            HttpMethod.POST,
            request,
            DetailedBillResponse.class
        );

        assertEquals(200, response.getStatusCodeValue());

        DetailedBillResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(0.0, responseBody.getTotalAmount(), 0.01);
        assertEquals(2, responseBody.getItems().size());
    }
}
