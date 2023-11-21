package com.sjincho.hun.delivery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfo {

    @Column(name = "orders_id", nullable = false)
    private Long orderId;

    @Embedded
    private Receiver receiver;

    @Embedded
    private Address address;

    public OrderInfo(final Long orderId, final Receiver receiver, final Address address) {
        this.orderId = orderId;
        this.receiver = receiver;
        this.address = address;
    }
}
