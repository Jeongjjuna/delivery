package com.sjincho.delivery.food.dto;

import com.sjincho.delivery.food.domain.Food;

public class FoodResponse {
    private final Integer id;
    private final String name;
    private final String foodType;
    private final int price;

    public FoodResponse(Integer id, String name, String foodType, int price) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public static FoodResponse from(Food food) {
        return new FoodResponse(
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
