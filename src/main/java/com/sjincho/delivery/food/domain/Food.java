package com.sjincho.delivery.food.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Food {
    @Id
    private Long id;
    private String name;
    private String foodType;
    private Long price;

    public Food() {
    }

    public Food(final String name, final String foodType, final Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public static Food create(final String name, final String foodType, final Long price) {
        return new Food(name, foodType, price);
    }

    public void update(final String name, final String foodType, final Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
