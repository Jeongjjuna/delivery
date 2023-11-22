package com.sjincho.hun.delivery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
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
