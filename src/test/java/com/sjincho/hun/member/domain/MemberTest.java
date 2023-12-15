package com.sjincho.hun.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.sjincho.hun.member.exception.UnAuthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("Member 도메인과 요청자 id가 다르면 예외를 발생시킨다.")
    @Test
    void checkSameMemberException() {
        Member member = Member.builder()
                .id(1L)
                .name("홍길동")
                .email("email@gmail.com")
                .password("password")
                .cellPhone("010-1111-2222")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Long requestId = 2L;

        assertThatCode(() -> member.checkSameMember(requestId))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @DisplayName("Member 도메인과 요청자 id가 같으면 예외를 발생시키지 않음")
    @Test
    void checkSameMember() {
        Member member = Member.builder()
                .id(1L)
                .name("홍길동")
                .email("email@gmail.com")
                .password("password")
                .cellPhone("010-1111-2222")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Long requestId = 1L;

        assertThatCode(() -> member.checkSameMember(requestId))
                .doesNotThrowAnyException();
    }

    @DisplayName("Member 도메인 생성 테스트")
    @Test
    void create() {
        assertThatCode(() -> Member.builder()
                .name("홍길동")
                .email("email@gmail.com")
                .password("password")
                .cellPhone("010-1111-2222")
                .memberRole(MemberRole.CUSTOMER)
                .build());
    }

    @DisplayName("Member 도메인 수정 테스트")
    @Test
    void update() {
        Member member = Member.builder()
                .id(1L)
                .name("홍길동")
                .email("email@gmail.com")
                .password("password")
                .cellPhone("010-1111-2222")
                .memberRole(MemberRole.CUSTOMER)
                .build();

        MemberUpdate memberUpdate = MemberUpdate.builder()
                .name("일지매")
                .email("updated@gmail.com")
                .password("010-1111-2222")
                .memberRole(MemberRole.CUSTOMER)
                .build();

        member.update(memberUpdate);

        assertAll(
                () -> assertThat(member.getName()).isEqualTo("일지매"),
                () -> assertThat(member.getEmail()).isEqualTo("updated@gmail.com")
        );
    }
}