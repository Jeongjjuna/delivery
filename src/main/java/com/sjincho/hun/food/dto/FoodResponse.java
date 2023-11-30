package com.sjincho.hun.food.dto;

import com.sjincho.hun.food.domain.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FoodResponse {
    private final Long id;
    private final String name;
    private final String foodType;
    private final Long price;

    @Builder
    private FoodResponse(final Long id, final String name, final String foodType, final Long price) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public static FoodResponse from(final Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .foodType(food.getFoodType())
                .price(food.getPrice())
                .build();
    }
}
