package com.sjincho.hun.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineTest {

    @DisplayName("OrderLine 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> OrderLine.builder()
                .foodId(1L)
                .price(8000L)
                .quantity(2L)
                .foodName("짜장면").build()
        ).doesNotThrowAnyException();
    }

    @DisplayName("OrderLine 주문 금액 계산 테스트")
    @Test
    void calculatePayment() {
        OrderLine orderLine = OrderLine.builder()
                .foodId(1L)
                .price(8000L)
                .quantity(2L)
                .foodName("짜장면").build();

        Long payments = orderLine.calculatePayment();

        assertThat(payments).isEqualTo(16000L);
    }

}