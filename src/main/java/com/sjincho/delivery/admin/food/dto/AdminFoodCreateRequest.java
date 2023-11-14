package com.sjincho.delivery.admin.food.dto;

import com.sjincho.delivery.food.domain.Food;

public class AdminFoodCreateRequest {
    private final String name;
    private final String foodType;
    private final Long price;

    public AdminFoodCreateRequest(String name, String foodType, Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public Food toEntity() {
        return new Food(name, foodType, price);
    }

    public String getName() {
        return name;
    }

    public String getFoodType() {
        return foodType;
    }

    public Long getPrice() {
        return price;
    }
}
