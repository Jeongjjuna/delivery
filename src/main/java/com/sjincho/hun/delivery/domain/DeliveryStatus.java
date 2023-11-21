package com.sjincho.hun.delivery.domain;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    READY_FOR_DELIVERY("ready_for_delivery"),
    DELIVERING("delivering"),
    COMPLETED("completed");

    private final String status;

    DeliveryStatus(final String status) {
        this.status = status;
    }
}
