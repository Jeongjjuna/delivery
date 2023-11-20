package com.sjincho.delivery.order.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrdererTest {

    @DisplayName("Orderer 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> new Orderer(1L, "010-1111-2222"))
                .doesNotThrowAnyException();
    }
}