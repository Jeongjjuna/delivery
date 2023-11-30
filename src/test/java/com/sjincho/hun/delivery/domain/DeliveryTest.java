package com.sjincho.hun.delivery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sjincho.hun.delivery.exception.DeliveryNotDeliveringException;
import com.sjincho.hun.delivery.exception.DeliveryNotReadyStatusException;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.order.domain.Address;
import com.sjincho.hun.order.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryTest {

    private Order order;

    @BeforeEach
    void setup() {
        order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(Member.builder().id(1L).cellPhone("010-1111-2222").build())
                .build();
    }

    @DisplayName("Delivery 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> Delivery.builder()
                .order(order)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build()
        ).doesNotThrowAnyException();
    }

    @DisplayName("배달 시작 테스트")
    @Test
    void start() {
        Delivery delivery = Delivery.builder()
                .order(order)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();

        delivery.start();

        assertThat(delivery.getDeliveryStatus()).isEqualTo(DeliveryStatus.DELIVERING);
    }

    @DisplayName("이미 배달중일때 배달시작 요청이 들어오면 예가외발생")
    @Test
    void start_exception() {
        Delivery delivery = Delivery.builder()
                .order(order)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();

        delivery.start();

        assertThatThrownBy(() -> delivery.start())
                .isInstanceOf(DeliveryNotReadyStatusException.class);
    }

    @DisplayName("배달 완료 테스트")
    @Test
    void complete() {
        Delivery delivery = Delivery.builder()
                .order(order)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();
        delivery.start();

        delivery.complete();

        assertThat(delivery.getDeliveryStatus()).isEqualTo(DeliveryStatus.COMPLETED);
    }

    @DisplayName("배달중이 아닌 상태에서 배달완료 요청이 들어오면 예외발생")
    @Test
    void complete_exception() {
        Delivery delivery = Delivery.builder()
                .order(order)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();

        assertThatThrownBy(() -> delivery.complete())
                .isInstanceOf(DeliveryNotDeliveringException.class);
    }

}