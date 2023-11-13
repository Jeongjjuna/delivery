package com.sjincho.delivery.admin.food.dto;

import com.sjincho.delivery.food.domain.Food;

public class AdminFoodRequest {
    private final String name;
    private final String foodType;
    private final int price;

    public AdminFoodRequest(String name, String foodType, int price) {
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

    public int getPrice() {
        return price;
    }
}
