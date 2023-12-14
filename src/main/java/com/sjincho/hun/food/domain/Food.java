package com.sjincho.hun.food.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Food {
    private Long id;
    private String name;
    private String foodType;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Food(final Long id, final String name, final String foodType,
                final Long price, final LocalDateTime createdAt,
                final LocalDateTime updatedAt, final LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Food from(FoodCreate foodCreate) {
        return Food.builder()
                .name(foodCreate.getName())
                .foodType(foodCreate.getFoodType())
                .price(foodCreate.getPrice())
                .build();
    }

    public void update(final FoodUpdate foodUpdate) {
        this.name = foodUpdate.getName();
        this.foodType = foodUpdate.getFoodType();
        this.price = foodUpdate.getPrice();
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }

    public Long calculate(Long quantity) {
        return price * quantity;
    }
}
