package com.sjincho.hun.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sjincho.hun.auth.dto.User;
import com.sjincho.hun.config.CustomerMockUser;
import com.sjincho.hun.config.OwnerMockUser;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.domain.MemberRole;
import com.sjincho.hun.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Member 도메인 API 테스트")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @AfterEach
    public void clean() {
        memberJpaRepository.deleteAll();
    }

    @DisplayName("회원수정 데이터 유효성 검사")
    @ParameterizedTest(name = "name:{0}, email:{1}, password:{2}, cellPhone:{3}, memberRole:{4} 회원가입 요청시 400 응답 반환")
    @MethodSource("provideRequestForInvalidData")
    void 회원수정_데이터_유효성_검사(String name, String email, String password, String cellPhone, String memberRole) throws Exception {
        // given
        String invalidJson = """
                    {
                      "name" : %s,
                      "email" : %s,
                      "password" : %s,
                      "cellPhone" : %s,
                      "memberRole" : %s
                    }
                    """.formatted(name, email, password, cellPhone, memberRole);

        // when, then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("회원 수정 테스트 : 존재하지 않는 회원의 정보 수정시 404 응답 반환")
    @Test
    @OwnerMockUser
    void 존재하지_않는_회원_정보_수정_요청() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        memberJpaRepository.save(member);

        String json = """
                {
                  "name" : "정지훈",
                  "email" : "user1@naver.com",
                  "password" : "password",
                  "cellPhone" : "010-1111-2222",
                  "memberRole" : "CUSTOMER"
                }
                """;

        // when, then
        mockMvc.perform(put("/members/{memberId}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("회원 수정 테스트 : 다른 사람의 회원 정보 수정시 403 응답 반환")
    @Test
    @OwnerMockUser
    void 다른사람의_회원_정보_수정_요청() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member saved = memberJpaRepository.save(member);

        String json = """
                {
                  "name" : "정지훈",
                  "email" : "user1@naver.com",
                  "password" : "password",
                  "cellPhone" : "010-1111-2222",
                  "memberRole" : "CUSTOMER"
                }
                """;

        // when, then
        mockMvc.perform(put("/members/{memberId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("회원 수정 테스트 : 음식점이 회원 수정시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_회원_수정_요청() throws Exception {
        // given
        String json = """
                {
                  "name" : "정지훈",
                  "email" : "user1@naver.com",
                  "password" : "password",
                  "cellPhone" : "010-1111-2222",
                  "memberRole" : "CUSTOMER"
                }
                """;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // when, then
        mockMvc.perform(put("/members/{memberId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("회원 수정 테스트 : 손님이 회원 수정시 200 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_회원_수정_요청() throws Exception {
        // given
        String json = """
                {
                  "name" : "정지훈",
                  "email" : "user1@naver.com",
                  "password" : "password",
                  "cellPhone" : "010-1111-2222",
                  "memberRole" : "CUSTOMER"
                }
                """;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        // when, then
        mockMvc.perform(put("/members/{memberId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("전체 회원 조회 테스트 : 손님이 전체 회원 조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_전체_회원_조회_요청() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        memberJpaRepository.save(member);

        // when, then
        mockMvc.perform(get("/members?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("전체 회원 조회 테스트 : 음식점이 전체 회원 조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_전체_회원_조회_요청() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        memberJpaRepository.save(member);

        // when, then
        mockMvc.perform(get("/members?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("나의 정보 조회 테스트 : 음식점이 나의 정보 조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_개인정보_조회_요청() throws Exception {
        // when, then
        mockMvc.perform(get("/members/my")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("나의 정보 조회 테스트 : 음식점이 나의 정보 조회시 200 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_개인정보_조회_요청() throws Exception {
        // when, then
        mockMvc.perform(get("/members/my")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 회원 조회 테스트 : 존재하지 않는 id 회원 조회시 404 응답 반환")
    @Test
    @OwnerMockUser
    void 존재하지_않는_id의_회원_조회_요청() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member saved = memberJpaRepository.save(member);

        // when, then
        mockMvc.perform(get("/members/{memberId}", 999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("특정 회원 조회 테스트 : 음식점이 특정 회원조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_음식조회_요청() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member saved = memberJpaRepository.save(member);

        // when, then
        mockMvc.perform(get("/members/{memberId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 회원 조회 테스트 : 손님이 특정 회원조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_회원조회_요청() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member saved = memberJpaRepository.save(member);

        // when, then
        mockMvc.perform(get("/members/{memberId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("회원가입 데이터 유효성 검사")
    @ParameterizedTest(name = "name:{0}, email:{1}, password:{2}, cellPhone:{3}, memberRole:{4} 회원가입 요청시 400 응답 반환")
    @MethodSource("provideRequestForInvalidData")
    void 회원가입_데이터_유효성_검사(String name, String email, String password, String cellPhone, String memberRole) throws Exception {
        // given
        String invalidJson = """
                {
                  "name" : %s,
                  "email" : %s,
                  "password" : %s,
                  "cellPhone" : %s,
                  "memberRole" : %s
                }
                """.formatted(name, email, password, cellPhone, memberRole);

        // when, then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("회원 가입 테스트 : 중복된 이메일로 회원 가입시 409 응답 반환")
    @Test
    void 회원_가입시_중복된_이메일로_가입() throws Exception {
        // given
        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        memberJpaRepository.save(member);

        String json = """
                {
                  "name" : "정지훈",
                  "email" : "user1@naver.com",
                  "password" : "password",
                  "cellPhone" : "010-1111-2222",
                  "memberRole" : "CUSTOMER"
                }
                """;

        // when, then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @DisplayName("회원 가입 테스트 : 회원 가입시 201 응답 반환")
    @Test
    void 회원_가입() throws Exception {
        // given
        String json = """
                {
                  "name" : "정지훈",
                  "email" : "user1@naver.com",
                  "password" : "password",
                  "cellPhone" : "010-1111-2222",
                  "memberRole" : "CUSTOMER"
                }
                """;

        // when, then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private static Stream<Arguments> provideRequestForInvalidData() {
        return Stream.of(
                Arguments.of("", "user1@gmail.com", "pw", "000-0000-0000", "CUSTOMER"),
                Arguments.of("name", "", "pw", "000-0000-0000", "CUSTOMER"),
                Arguments.of("name", "user1@gmail.com", "", "000-0000-0000", "CUSTOMER"),
                Arguments.of("name", "user1@gmail.com", "pw", "", "CUSTOMER"),
                Arguments.of("name", "user1@gmail.com", "pw", "000-0000-0000", ""),

                Arguments.of(null, "user1@gmail.com", "pw", "000-0000-0000", "CUSTOMER"),
                Arguments.of("name", null, "pw", "000-0000-0000", "CUSTOMER"),
                Arguments.of("name", "user1@gmail.com", null, "000-0000-0000", "CUSTOMER"),
                Arguments.of("name", "user1@gmail.com", "pw", null, "CUSTOMER"),
                Arguments.of("name", "user1@gmail.com", "pw", "000-0000-0000", null)
        );
    }

}