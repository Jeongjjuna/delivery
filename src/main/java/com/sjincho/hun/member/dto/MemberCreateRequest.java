package com.sjincho.hun.member.dto;

import com.sjincho.hun.member.domain.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateRequest {

    @NotBlank(message = "이름을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String name;

    @NotBlank(message = "이메일을 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String email;

    @NotBlank(message = "비밀번호를 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String password;

    @NotBlank(message = "전화번호를 확인해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String cellPhone;

    @NotNull(message = "회원유형을 확인해주세요. null 일 수 없습니다.")
    private final MemberRole memberRole;

    @Builder
    public MemberCreateRequest(final String name, final String email, final String password,
                               final String cellPhone, final MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellPhone = cellPhone;
        this.memberRole = memberRole;
    }
}