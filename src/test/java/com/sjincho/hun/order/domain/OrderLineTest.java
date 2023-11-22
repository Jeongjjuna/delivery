package com.sjincho.hun.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineTest {

    @DisplayName("OrderLine 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> OrderLine.create(
                1L, 8000L,
                2L, "짜장면"))
                .doesNotThrowAnyException();
    }

    @DisplayName("OrderLine 주문 금액 계산 테스트")
    @Test
    void calculatePayment() {
        OrderLine orderLine = new OrderLine(
                1L, 8000L,
                2L, "짜장면");

        Long payments = orderLine.calculatePayment();

        assertThat(payments).isEqualTo(16000L);
    }

}