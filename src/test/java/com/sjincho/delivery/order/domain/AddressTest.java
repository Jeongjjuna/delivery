package com.sjincho.delivery.order.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    @DisplayName("Address 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> new Address("123456", "101동 1001호"))
                .doesNotThrowAnyException();
    }
}