package com.sjincho.delivery.admin.food.dto;

import com.sjincho.delivery.food.domain.Food;

public class AdminFoodResponse {
    private final Integer id;
    private final String name;
    private final String foodType;
    private final int price;

    public AdminFoodResponse(Integer id, String name, String foodType, int price) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public static AdminFoodResponse from(Food food) {
        return new AdminFoodResponse(
                food.getId(),
                food.getName(),
                food.getFoodType(),
                food.getPrice()
        );
    }

    public Integer getId() {
        return id;
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
