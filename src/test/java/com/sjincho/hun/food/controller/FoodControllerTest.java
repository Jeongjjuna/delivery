package com.sjincho.hun.food.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjincho.hun.config.CustomerMockUser;
import com.sjincho.hun.config.OwnerMockUser;
import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.repository.FoodJpaRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Food 도메인 API 테스트")
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FoodJpaRepository foodJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @AfterEach
    public void clean() {
        foodJpaRepository.deleteAll();
        memberJpaRepository.deleteAll();
    }

    @DisplayName("특정 음식 조회 테스트 : 존재하지 않는 id 음식 조회시 404 응답 반환")
    @Test
    @OwnerMockUser
    void 존재하지_않는_id의_음식조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food saved = foodJpaRepository.save(food);

        // when, then
        mockMvc.perform(get("/foods/{foodId}", 2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("특정 음식 조회 테스트 : 음식점이 음식조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_음식조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food saved = foodJpaRepository.save(food);

        // when, then
        mockMvc.perform(get("/foods/{foodId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 음식 조회 테스트 : 손님이 음식조회시 200 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_음식조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food saved = foodJpaRepository.save(food);

        // when, then
        mockMvc.perform(get("/foods/{foodId}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("음식 등록 데이터 유효성 검사")
    @ParameterizedTest(name = "name:{0}, foodType:{1}, price:{2} 음식 등록 요청시 401 응답 반환")
    @MethodSource("provideRequestForInvalidData")
    @OwnerMockUser
    void 음식_등록_데이터_유효성_검사(String name, String foodType, String price) throws Exception {
        // given
        String invalidJson = """
                {
                  "name" : %s,
                  "foodType" : %s,
                  "price" : %s
                }
                """.formatted(name, foodType, price);

        // when, then
        mockMvc.perform(post("/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    private static Stream<Arguments> provideRequestForInvalidData() {
        return Stream.of(
                Arguments.of(null, "한식", "10000"),
                Arguments.of("볶음밥", null, "10000"),
                Arguments.of("볶음밥", "한식", null),
                Arguments.of("볶음밥", "한식", "-1")
        );
    }

    @DisplayName("음식 등록 테스트 : 음식점이 음식등록시 201 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_음식_등록() throws Exception {
        // given
        String json = """
                {
                  "name" : "볶음밥",
                  "price" : 10000,
                  "foodType" : "한식"
                }
                """;

        // when, then
        mockMvc.perform(post("/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("음식 등록 테스트 : 손님이 음식등록시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_음식_등록() throws Exception {
        // given
        String json = """
                {
                  "name" : "볶음밥",
                  "price" : 10000,
                  "foodType" : "한식"
                }
                """;

        // when, then
        mockMvc.perform(post("/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}