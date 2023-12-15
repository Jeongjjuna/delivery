package com.sjincho.hun.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sjincho.hun.food.domain.Food;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class OrderLinesTest {

    @DisplayName("OrderLines 객체의 총 주문 금액을 반환하는 테스트")
    @Test
    void getPaymentAmount() {
        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine1 = OrderLine.builder()
                .food(Food.builder().price(2000L).build())
                .quantity(2L)
                .build();
        OrderLine orderLine2 = OrderLine.builder()
                .food(Food.builder().price(3000L).build())
                .quantity(3L)
                .build();
        orderLines.add(orderLine1);
        orderLines.add(orderLine2);

        Long result = new OrderLines(orderLines).getPaymentAmount();

        assertThat(result).isEqualTo(13000L);
    }
}