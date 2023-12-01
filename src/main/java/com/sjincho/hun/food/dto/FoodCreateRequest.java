package com.sjincho.hun.food.dto;

import com.sjincho.hun.food.domain.Food;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FoodCreateRequest {

    @NotBlank(message = "음식상품 이름을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String name;

    @NotBlank(message = "음식상품 유형을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String foodType;

    @NotNull(message = "음식상품 가격을 확인해주세요. null 일 수 없습니다.")
    @PositiveOrZero(message = "음식상품 가격은 0보다 같거나 커야합니다.")
    private final Long price;

    @Builder
    public FoodCreateRequest(final String name, final String foodType, final Long price) {
        this.name = name;
        this.foodType = foodType;
        this.price = price;
    }

    public Food toEntity() {
        return Food.builder()
                .name(name)
                .foodType(foodType)
                .price(price)
                .build();
    }
}
