package com.sjincho.hun.food.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "food")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "food_type", nullable = false)
    private String foodType;

    @Column(name = "price", nullable = false)
    private Long price;

    @Builder
    public Food(final String name, final String foodType, final Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public void update(final String name, final String foodType, final Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }
}
