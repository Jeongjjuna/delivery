package com.sjincho.delivery.admin.food.dto;

import com.sjincho.delivery.food.domain.Food;
import lombok.Getter;

@Getter
public class AdminFoodResponse {
    private final Long id;
    private final String name;
    private final String foodType;
    private final Long price;

    public AdminFoodResponse(final Long id, final String name, final String foodType, final Long price) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public static AdminFoodResponse from(final Food food) {
        return new AdminFoodResponse(
                food.getId(),
                food.getName(),
                food.getFoodType(),
                food.getPrice()
        );
    }
}
