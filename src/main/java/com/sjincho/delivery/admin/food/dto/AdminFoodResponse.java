package com.sjincho.delivery.admin.food.dto;

import com.sjincho.delivery.food.domain.Food;
import lombok.Getter;

@Getter
public class AdminFoodResponse {
    private final Long id;
    private final String name;
    private final String foodType;
    private final Long price;

    public AdminFoodResponse(Long id, String name, String foodType, Long price) {
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
}
