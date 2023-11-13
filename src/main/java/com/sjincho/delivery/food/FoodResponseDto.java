package com.sjincho.delivery.food;

public class FoodResponseDto {
    private final Integer id;
    private final String name;
    private final String foodType;
    private final int price;

    public FoodResponseDto(Integer id, String name, String foodType, int price) {
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public static FoodResponseDto from(Food food) {
        return new FoodResponseDto(
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
