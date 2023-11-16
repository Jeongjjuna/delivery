package com.sjincho.delivery.member.dto;

import com.sjincho.delivery.member.domain.Member;
import com.sjincho.delivery.member.domain.MemberRole;
import lombok.Getter;

@Getter
public class MemberResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String cellPhone;
    private final MemberRole memberRole;

    public MemberResponse(final Long id, final String name,
                          final String email, final String cellPhone,
                          final MemberRole memberRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
        this.memberRole = memberRole;
    }

    public static MemberResponse from(final Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCellPhone(),
                member.getMemberRole()
        );
    }
}
