package com.sjincho.hun.member.infastructure.entity;

import com.sjincho.hun.common.domain.BaseEntity;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.domain.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at IS NULL")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "cell_phone", nullable = false)
    private String cellPhone;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public static MemberEntity from(Member member) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.id = member.getId();
        memberEntity.name = member.getName();
        memberEntity.email = member.getEmail();
        memberEntity.password = member.getPassword();
        memberEntity.cellPhone = member.getCellPhone();
        memberEntity.memberRole = member.getMemberRole();
        return memberEntity;
    }

    public Member toModel() {
        return Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .cellPhone(cellPhone)
                .memberRole(memberRole)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
