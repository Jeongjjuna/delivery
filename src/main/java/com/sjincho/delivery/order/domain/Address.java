package com.sjincho.delivery.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    public Address(final String postalCode, final String detailAddress) {
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
    }
}
