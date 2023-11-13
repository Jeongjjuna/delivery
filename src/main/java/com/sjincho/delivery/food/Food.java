package com.sjincho.delivery.food;


public class Food {
    private Integer id;
    private String name;
    private String foodType;
    private int price;

    public Food(String name, String foodType, int price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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
