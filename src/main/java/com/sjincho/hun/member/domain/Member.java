package com.sjincho.hun.member.domain;

import com.sjincho.hun.common.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Member extends BaseEntity {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String cellPhone;
    private MemberRole memberRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Member(final Long id, final String name, final String email,
                  final String password, final String cellPhone,
                  final MemberRole memberRole, final LocalDateTime createdAt,
                  final LocalDateTime updatedAt, final LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellPhone = cellPhone;
        this.memberRole = memberRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public void update(final MemberUpdate memberUpdate) {
        this.name = memberUpdate.getName();
        this.email = memberUpdate.getEmail();
        this.password = memberUpdate.getPassword();
        this.cellPhone = memberUpdate.getCellPhone();
        this.memberRole = memberUpdate.getMemberRole();
    }

    public void applyEncodedPassword(final String encodedPassword) {
        this.password = encodedPassword;
    }
}
