package com.sjincho.delivery.food;


public class Food {
    private Integer id;
    private String name;
    private int price;
    private String foodType;

    public Food(String name, int price, String foodType) {
        this.name = name;
        this.price = price;
        this.foodType = foodType;
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
