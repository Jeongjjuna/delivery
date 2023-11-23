package com.sjincho.hun.delivery.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sjincho.hun.delivery.exception.DeliveryNotDeliveringException;
import com.sjincho.hun.delivery.exception.DeliveryNotReadyStatusException;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.domain.MemberRole;
import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DeliveryTest {

    private Order order;
    private Member receiver;

    @BeforeEach
    void setup() {
        List<OrderLine> orderLines = new ArrayList<>(Arrays.asList(
                new OrderLine(1L, 9000L, 2L, "짜장면"),
                new OrderLine(2L, 8000L, 1L, "짬뽕")
        ));
        order = Order.create(
                1L, "010-1111-2222",
                "123445", "101동 1001호", orderLines);

        receiver = Member.builder()
                .name("홍길동")
                .email("email@gmail.com")
                .password("password")
                .cellPhone("010-1111-2222")
                .memberRole(MemberRole.CUSTOMER)
                .build();
    }

    @DisplayName("Delivery 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> Delivery.create(order, receiver))
                .doesNotThrowAnyException();
    }

    @DisplayName("배달 시작 테스트")
    @Test
    void start() {
        Delivery delivery = Delivery.create(order, receiver);

        delivery.start();

        assertThat(delivery.getDeliveryStatus()).isEqualTo(DeliveryStatus.DELIVERING);
    }

    @DisplayName("이미 배달중일때 배달시작 요청이 들어오면 예가외발생")
    @Test
    void start_exception() {
        Delivery delivery = Delivery.create(order, receiver);
        delivery.start();

        assertThatThrownBy(() -> delivery.start())
                .isInstanceOf(DeliveryNotReadyStatusException.class);
    }

    @DisplayName("배달 완료 테스트")
    @Test
    void complete() {
        Delivery delivery = Delivery.create(order, receiver);
        delivery.start();

        delivery.complete();

        assertThat(delivery.getDeliveryStatus()).isEqualTo(DeliveryStatus.COMPLETED);
    }

    @DisplayName("배달중이 아닌 상태에서 배달완료 요청이 들어오면 예외발생")
    @Test
    void complete_exception() {
        Delivery delivery = Delivery.create(order, receiver);

        assertThatThrownBy(() -> delivery.complete())
                .isInstanceOf(DeliveryNotDeliveringException.class);
    }

}