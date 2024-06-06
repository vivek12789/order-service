package com.prgx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "prices")
public class PricesConfig {

    private Map<String, Double> coffee;
    private Map<String, Double> addon;
    private Map<String, Double> cookie;

    public Map<String, Double> getCoffee() {
        return coffee;
    }

    public void setCoffee(Map<String, Double> coffee) {
        this.coffee = coffee;
    }

    public Map<String, Double> getAddon() {
        return addon;
    }

    public void setAddon(Map<String, Double> addon) {
        this.addon = addon;
    }

    public Map<String, Double> getCookie() {
        return cookie;
    }

    public void setCookie(Map<String, Double> cookie) {
        this.cookie = cookie;
    }
}

