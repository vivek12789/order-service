package com.prgx.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
public class AddOn {
    private String name;

    // Constructor with arguments (if needed)
    public AddOn(String name) {
        this.name = name;
    }
}
