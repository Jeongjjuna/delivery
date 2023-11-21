package com.sjincho.hun.member.domain;

import lombok.Getter;

@Getter
public enum MemberRole {
    CUSTOMER("customer"),
    OWNER("owner");

    private final String name;

    MemberRole(String name) {
        this.name = name;
    }
}