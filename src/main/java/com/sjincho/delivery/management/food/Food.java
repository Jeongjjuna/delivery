package com.sjincho.delivery.management.food;


public class Food {
    private String name;
    private int price;
    private String foodType;

    public Food(String name, int price, String foodType) {
        this.name = name;
        this.price = price;
        this.foodType = foodType;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getFoodType() {
        return foodType;
    }
}