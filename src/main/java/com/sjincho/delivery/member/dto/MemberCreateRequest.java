package com.sjincho.delivery.member.dto;

import com.sjincho.delivery.member.domain.MemberRole;
import lombok.Getter;

@Getter
public class MemberCreateRequest {
    private final String name;
    private final String email;
    private final String password;
    private final String cellPhone;
    private final MemberRole memberRole;

    public MemberCreateRequest(final String name, final String email, final String password,
                               final String cellPhone, final MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellPhone = cellPhone;
        this.memberRole = memberRole;
    }
}