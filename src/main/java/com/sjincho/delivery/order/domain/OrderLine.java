package com.sjincho.delivery.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {

    @Column(name = "food_id", nullable = false)
    private Long foodId;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    public OrderLine(final Long foodId, final Long price,
                     final Long quantity, final String foodName) {
        this.foodId = foodId;
        this.price = price;
        this.quantity = quantity;
        this.foodName = foodName;
    }

    public static OrderLine create(final Long foodId, final Long price,
                                   final Long quantity, final String foodName) {
        return new OrderLine(
                foodId,
                price,
                quantity,
                foodName
        );
    }

    public Long calculatePayment() {
        return price*quantity;
    }
}
