package com.sjincho.hun.order.domain;

import lombok.Getter;

@Getter
public class Address {
    private String postalCode;
    private String detailAddress;

    public Address(final String postalCode, final String detailAddress) {
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
    }
}
