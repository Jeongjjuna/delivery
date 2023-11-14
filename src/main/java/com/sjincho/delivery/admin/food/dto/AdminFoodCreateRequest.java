package com.sjincho.delivery.admin.food.dto;

import lombok.Getter;

@Getter
public class AdminFoodCreateRequest {
    private final String name;
    private final String foodType;
    private final Long price;

    public AdminFoodCreateRequest(final String name, final String foodType, final Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }
}
