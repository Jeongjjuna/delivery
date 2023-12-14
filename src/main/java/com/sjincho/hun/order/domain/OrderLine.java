package com.sjincho.hun.order.domain;

import com.sjincho.hun.food.domain.Food;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderLine {
    private Long id;
    private Order order;
    private Food food;
    private Long quantity;

    @Builder
    public OrderLine(final Long id, final Order order,
                     final Food food, final Long quantity) {
        this.id = id;
        this.order = order;
        this.food = food;
        this.quantity = quantity;
    }

    public static OrderLine from(OrderLineCreate orderLineCreate,
                                 Order order, Food food) {
        return OrderLine.builder()
                .order(order)
                .food(food)
                .quantity(orderLineCreate.getQuantity())
                .build();
    }

    public Long calculatePayment() {
        return food.calculate(quantity);
    }
}
