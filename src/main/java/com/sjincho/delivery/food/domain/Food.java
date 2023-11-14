package com.sjincho.delivery.food.domain;


public class Food {
    private Long id;
    private String name;
    private String foodType;
    private Long price;

    public Food(String name, String foodType, Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFoodType() {
        return foodType;
    }

    public Long getPrice() {
        return price;
    }
}
