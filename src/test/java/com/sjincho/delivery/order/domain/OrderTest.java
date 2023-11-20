package com.sjincho.delivery.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sjincho.delivery.order.exception.OrderNotAcceptingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class OrderTest {

    private List<OrderLine> orderLines;

    private Order createtTestOrder() {
        return Order.create(
                1L, "010-1111-2222",
                "123445", "101동 1001호", orderLines);
    }

    @BeforeEach
    void setup() {
        orderLines = new ArrayList<>(Arrays.asList(
                new OrderLine(1L, 9000L, 2L, "짜장면"),
                new OrderLine(2L, 8000L, 1L, "짬뽕")
        ));
    }

    @DisplayName("Order 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> Order.create(
                1L, "010-1111-2222",
                "123445", "101동 1001호", orderLines))
                .doesNotThrowAnyException();
    }

    @DisplayName("Order 전체 주문 금액 계산 테스트")
    @Test
    void calculatePaymentsAmount() {
        Order order = createtTestOrder();

        Long totalPayments = order.calculatePaymentsAmount();

        assertThat(totalPayments).isEqualTo(26000L);
    }

    @DisplayName("주문 수락 테스트")
    @Test
    void approve() {
        Order order = createtTestOrder();

        order.approve();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("이미 수락된 주문에대해 수락 요청이 들어오면 예외발생")
    @Test
    void approve_exception() {
        Order order = createtTestOrder();

        order.approve();

        assertThatThrownBy(() -> order.approve())
                .isInstanceOf(OrderNotAcceptingException.class);
    }

    @DisplayName("주문 거절 테스트")
    @Test
    void reject() {
        Order order = createtTestOrder();

        order.reject();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.REJECTED);
    }

    @DisplayName("이미 수락된 주문에대해 거절 요청이 들어오면 예외발생")
    @Test
    void reject_exception() {
        Order order = createtTestOrder();

        order.approve();

        assertThatThrownBy(() -> order.reject())
                .isInstanceOf(OrderNotAcceptingException.class);
    }

    @DisplayName("주문 취소 테스트")
    @Test
    void cancel() {
        Order order = createtTestOrder();

        order.cancel();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELED);
    }

    @DisplayName("이미 수락된 주문에대해 취소 요청이 들어오면 예외발생")
    @Test
    void cancel_exception() {
        Order order = createtTestOrder();

        order.approve();

        assertThatThrownBy(() -> order.cancel())
                .isInstanceOf(OrderNotAcceptingException.class);
    }

}