package com.sjincho.delivery.food.domain;

import lombok.Getter;

@Getter
public class Food {
    private Long id;
    private String name;
    private String foodType;
    private Long price;

    public Food(String name, String foodType, Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public static Food create(String name, String foodType, Long price) {
        return new Food(name, foodType, price);
    }

    public void update(String name, String foodType, Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
