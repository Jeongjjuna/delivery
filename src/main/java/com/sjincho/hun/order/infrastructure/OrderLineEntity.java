package com.sjincho.hun.order.infrastructure;

import com.sjincho.hun.food.infrastructure.FoodEntity;
import com.sjincho.hun.order.domain.OrderLine;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "order_line")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private FoodEntity foodEntity;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    public static OrderLineEntity from(OrderLine orderLine) {
        OrderLineEntity orderLineEntity = new OrderLineEntity();
        orderLineEntity.id = orderLine.getId();
        orderLineEntity.orderEntity = OrderEntity.from(orderLine.getOrder());
        orderLineEntity.foodEntity = FoodEntity.from(orderLine.getFood());
        orderLineEntity.quantity = orderLine.getQuantity();
        return orderLineEntity;
    }

    public OrderLine toModel() {
        return OrderLine.builder()
                .id(id)
                .order(orderEntity.toModel())
                .food(foodEntity.toModel())
                .quantity(quantity)
                .build();
    }

}
