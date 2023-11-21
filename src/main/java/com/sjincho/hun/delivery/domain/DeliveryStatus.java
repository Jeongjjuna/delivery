package com.sjincho.hun.delivery.domain;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    DELIVERING("delivering"),
    COMPLETED("completed");

    private final String status;

    DeliveryStatus(final String status) {
        this.status = status;
    }
}
