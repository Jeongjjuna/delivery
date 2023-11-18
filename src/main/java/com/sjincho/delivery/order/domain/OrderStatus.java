package com.sjincho.delivery.order.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ACCEPTING("accepting"),
    ACCEPTED("accepted"),
    CANCELED("canceled"),
    REJECTED("rejected");

    private final String status;

    OrderStatus(final String status) {
        this.status = status;
    }
}
