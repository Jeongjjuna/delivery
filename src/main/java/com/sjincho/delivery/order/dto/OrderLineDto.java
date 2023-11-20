package com.sjincho.delivery.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class OrderLineDto {

    @NotNull(message = "주문음식 id를 확인해주세요. null 일 수 없습니다.")
    @PositiveOrZero(message = "id 는 음수일 수 없습니다.")
    private final Long foodId;

    @NotBlank(message = "주문상품 가격을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    @PositiveOrZero(message = "주문상품 가격은 0보다 같거나 커야합니다.")
    private final Long price;

    @NotBlank(message = "주문상품 수량을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    @Positive(message = "주문상품 수량은 1보다 같거나 커야합니다.")
    private final Long quantity;

    @NotBlank(message = "주문상품 이름을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String foodName;

    public OrderLineDto(final Long foodId, final Long price,
                        final Long quantity, final String foodName) {
        this.foodId = foodId;
        this.price = price;
        this.quantity = quantity;
        this.foodName = foodName;
    }

    public static OrderLineDto from(final Long foodId, final Long price,
                                    final Long quantity, final String foodName) {
        return new OrderLineDto(foodId, price, quantity, foodName);
    }
}
