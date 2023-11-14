package com.sjincho.delivery.food.dto;

import com.sjincho.delivery.food.domain.Food;
import lombok.Getter;

@Getter
public class FoodResponse {
    private final Long id;
    private final String name;
    private final String foodType;
    private final Long price;

    public FoodResponse(Long id, String name, String foodType, Long price) {
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
}
