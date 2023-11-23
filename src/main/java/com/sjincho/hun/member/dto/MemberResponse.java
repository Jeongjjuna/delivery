package com.sjincho.hun.member.dto;

import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String cellPhone;
    private final MemberRole memberRole;

    @Builder
    private MemberResponse(final Long id, final String name, final String email,
                           final String cellPhone, final MemberRole memberRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
        this.memberRole = memberRole;
    }

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getName())
                .cellPhone(member.getCellPhone())
                .memberRole(member.getMemberRole())
                .build();
    }
}
