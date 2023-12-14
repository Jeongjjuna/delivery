package com.sjincho.hun.food.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FoodTest {

    @DisplayName("Food 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build()
        ).doesNotThrowAnyException();
    }

    @DisplayName("Food 도메인 수정 테스트")
    @Test
    void update() {
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();

        FoodUpdate foodUpdate = FoodUpdate.builder()
                .name("슈퍼짜장면")
                .foodType("슈퍼중식")
                .price(8000L)
                .build();

        food.update(foodUpdate);

        assertAll(
                () -> assertThat(food.getName()).isEqualTo("슈퍼짜장면"),
                () -> assertThat(food.getFoodType()).isEqualTo("슈퍼중식"),
                () -> assertThat(food.getPrice()).isEqualTo(8000L)
        );
    }

}