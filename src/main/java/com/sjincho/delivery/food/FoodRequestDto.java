package com.sjincho.delivery.food;

public class FoodRequestDto {
    private final String name;
    private final String foodType;
    private final int price;

    public FoodRequestDto(final String name, final String foodType, final int price) {
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
