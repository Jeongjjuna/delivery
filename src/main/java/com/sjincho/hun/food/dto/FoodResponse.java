package com.sjincho.hun.food.dto;

import com.sjincho.hun.food.domain.Food;
import lombok.Getter;

@Getter
public class FoodResponse {
    private final Long id;
    private final String name;
    private final String foodType;
    private final Long price;

    public FoodResponse(final Long id, final String name, final String foodType, final Long price) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public static FoodResponse from(final Food food) {
        return new FoodResponse(
                food.getId(),
                food.getName(),
                food.getFoodType(),
                food.getPrice()
        );
    }
}
