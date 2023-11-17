package com.sjincho.delivery.member.domain;

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

@Entity(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

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

    public Member(final String name, final String email, final String password,
                  final String cellPhone, final MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellPhone = cellPhone;
        this.memberRole = memberRole;
    }

    public static Member create(final String name, final String email, final String password,
                                final String cellPhone, final MemberRole memberRole) {
        return new Member(name, email, password, cellPhone, memberRole);
    }

    public void update(final String name, final String email, final String password,
                       final String cellPhone, final MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellPhone = cellPhone;
        this.memberRole = memberRole;
    }
}
