package com.sjincho.hun.food.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjincho.hun.config.CustomerMockUser;
import com.sjincho.hun.config.OwnerMockUser;
import com.sjincho.hun.food.dto.FoodCreateRequest;
import com.sjincho.hun.food.repository.FoodJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Food 도메인 API 테스트")
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FoodJpaRepository foodJpaRepository;

    @AfterEach
    public void clean() {
        foodJpaRepository.deleteAll();
    }

    @DisplayName("Owner가 음식 등록 요청시 201 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_음식_등록() throws Exception {
        // given
        FoodCreateRequest request = FoodCreateRequest.builder()
                .name("볶음밥")
                .foodType("한식")
                .price(10000L)
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when, then
        mockMvc.perform(post("/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("Customer가 음식 등록 요청시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_음식_등록() throws Exception {
        // given
        FoodCreateRequest request = FoodCreateRequest.builder()
                .name("볶음밥")
                .foodType("한식")
                .price(10000L)
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when, then
        mockMvc.perform(post("/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}