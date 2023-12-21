package com.sjincho.hun.order.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sjincho.hun.auth.dto.User;
import com.sjincho.hun.config.CustomerMockUser;
import com.sjincho.hun.config.OwnerMockUser;
import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.service.port.FoodRepository;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.domain.MemberRole;
import com.sjincho.hun.member.service.port.MemberRepository;
import com.sjincho.hun.order.domain.Address;
import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderLine;
import com.sjincho.hun.order.domain.OrderStatus;
import com.sjincho.hun.order.service.port.OrderLineRepository;
import com.sjincho.hun.order.service.port.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Order 도메인 API 테스트")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    private Food setFood() {
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        return foodRepository.save(food);
    }

    private Member setMember() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return memberRepository.findById(user.getId()).get();
    }

    private Order setOrder(Member savedMember) {
        Order order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(savedMember)
                .orderStatus(OrderStatus.ACCEPTING)
                .build();
        return orderRepository.save(order);
    }

    private OrderLine setOrderLine(Food savedFood, Order savedOrder) {
        OrderLine orderLine = OrderLine.builder()
                .order(savedOrder)
                .food(savedFood)
                .quantity(2L)
                .build();
        return orderLineRepository.save(orderLine);
    }

    @DisplayName("주문 거절 하기 테스트 : 손님이 주문 거절 요청시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_주문_거절_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(patch("/orders/{orderId}/reject", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("주문 거절 하기 테스트 : 음식점이 주문 거절 요청시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_주문_거절_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(patch("/orders/{orderId}/reject", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("주문 승인 하기 테스트 : 손님이 주문 승인 요청시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_주문_승인_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(patch("/orders/{orderId}/accept", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("주문 승인 하기 테스트 : 음식점이 주문 승인 요청시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_주문_승인_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(patch("/orders/{orderId}/accept", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("주문 취소 하기 테스트 : 주문자가 아닌사람이 주문취소 요청시 401 응답 반환")
    @Test
    @CustomerMockUser
    void 주문자가_아닌사람이_주문_취소_하기() throws Exception {
        // given
        Food savedFood = setFood();

        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member savedMember = memberRepository.save(member);

        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(patch("/orders/{orderId}/cancel", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("주문 취소 하기 테스트 : 손님이 주문취소 요청시 200 응답 반환")
    @Test
    @CustomerMockUser
    void 주문_취소_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(patch("/orders/{orderId}/cancel", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("주문 하기 테스트 : 존재하지 않는 음식으로 주문요청시 404 응답 반환")
    @Test
    @CustomerMockUser
    void 존재하지_않는_음식_주문_하기() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();

        String json = """
                {
                  "postalCode" : "62334",
                  "detailAddress" : "서울특별시",
                  "orderLineCreates" : [
                      {
                      "foodId" : %s,
                      "quantity" : 2
                      }
                    ]
                }
                """.formatted(999999L);

        // when, then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("주문 하기 테스트 : 손님이 주문 요청시 201 응답 반환")
    @Test
    @CustomerMockUser
    void 주문_하기() throws Exception {
        // given
        Food savedFood = setFood();

        String json = """
                {
                  "postalCode" : "62334",
                  "detailAddress" : "서울특별시",
                  "orderLineCreates" : [
                      {
                      "foodId" : %s,
                      "quantity" : 2
                      }
                    ]
                }
                """.formatted(savedFood.getId());

        // when, then
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("접수중인 주문 목록 조회 테스트 : 손님이 접수중인 주문목록 조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_접수중인_조회_목록조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders/in-accept")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("접수중인 주문 목록 조회 테스트 : 음식점이 접수중인 주문목록 조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_접수중인_주문_목록조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders/in-accept")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 회원의 주문 목록 조회 테스트 : 손님이 특정 회원의 주문목록 조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_특정회원의_조회_목록조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders/members/{memberId}", savedMember.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("특정 회원의 주문 목록 조회 테스트 : 음식점이 특정 회원의 주문목록 조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_특정회원의_주문_목록조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders/members/{memberId}", savedMember.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("전체 주문 조회 테스트 : 손님이 전체 주문 조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_전체_주문_조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("전체 주문 조회 테스트 : 음식점이 전체 주문 조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_전체_주문_조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 회원 조회 테스트 : 존재하지 않는 id 주문 조회시 404 응답 반환")
    @Test
    @OwnerMockUser
    void 존재하지_않는_id의_주문_조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders/{orderId}", 999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("특정 주문 조회 테스트 : 음식점이 특정 주문조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_주문_조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders/{orderId}", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 주문 조회 테스트 : 손님이 특정 주문조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_주문_조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(get("/orders/{orderId}", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}