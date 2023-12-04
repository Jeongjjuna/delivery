package com.sjincho.hun.delivery.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sjincho.hun.config.CustomerMockUser;
import com.sjincho.hun.config.OwnerMockUser;
import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import com.sjincho.hun.delivery.repository.DeliveryJpaRepository;
import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.repository.FoodJpaRepository;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.domain.MemberRole;
import com.sjincho.hun.member.repository.MemberJpaRepository;
import com.sjincho.hun.order.domain.Address;
import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderLine;
import com.sjincho.hun.order.repository.OrderJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Delivery 도메인 API 테스트")
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FoodJpaRepository foodJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private DeliveryJpaRepository deliveryJpaRepository;

    @DisplayName("배달중인 주문 목록 조회 테스트 : 손님이 배달중인 주문목록 조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_배달중인_조회_목록조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food savedFood = foodJpaRepository.save(food);

        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member savedMember = memberJpaRepository.save(member);

        Order order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(savedMember)
                .build();
        order.addOrderLine(OrderLine.builder()
                .order(order)
                .food(savedFood)
                .quantity(2L)
                .build()
        );
        Order savedOrder = orderJpaRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.DELIVERING)
                .deliveryStartedAt(null)
                .build();

        // when, then
        mockMvc.perform(get("/deliveries", 999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("배달중인 주문 목록 조회 테스트 : 음식점이 배달중인 주문목록 조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_배달중인_주문_목록조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food savedFood = foodJpaRepository.save(food);

        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member savedMember = memberJpaRepository.save(member);

        Order order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(savedMember)
                .build();
        order.addOrderLine(OrderLine.builder()
                .order(order)
                .food(savedFood)
                .quantity(2L)
                .build()
        );
        Order savedOrder = orderJpaRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.DELIVERING)
                .deliveryStartedAt(null)
                .build();

        // when, then
        mockMvc.perform(get("/deliveries", 999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("전체 배달 조회 테스트 : 손님이 전체 배달 조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_전체_배달_조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food savedFood = foodJpaRepository.save(food);

        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member savedMember = memberJpaRepository.save(member);

        Order order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(savedMember)
                .build();
        order.addOrderLine(OrderLine.builder()
                .order(order)
                .food(savedFood)
                .quantity(2L)
                .build()
        );
        Order savedOrder = orderJpaRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();

        // when, then
        mockMvc.perform(get("/deliveries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("전체 배달 조회 테스트 : 음식점이 전체 배달 조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_전체_배달_조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food savedFood = foodJpaRepository.save(food);

        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member savedMember = memberJpaRepository.save(member);

        Order order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(savedMember)
                .build();
        order.addOrderLine(OrderLine.builder()
                .order(order)
                .food(savedFood)
                .quantity(2L)
                .build()
        );
        Order savedOrder = orderJpaRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();

        // when, then
        mockMvc.perform(get("/deliveries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 회원 조회 테스트 : 존재하지 않는 id 배달 조회시 404 응답 반환")
    @Test
    @OwnerMockUser
    void 존재하지_않는_id의_배달_조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food savedFood = foodJpaRepository.save(food);

        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member savedMember = memberJpaRepository.save(member);

        Order order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(savedMember)
                .build();
        order.addOrderLine(OrderLine.builder()
                .order(order)
                .food(savedFood)
                .quantity(2L)
                .build()
        );
        Order savedOrder = orderJpaRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();
        Delivery savedDelivery = deliveryJpaRepository.save(delivery);

        // when, then
        mockMvc.perform(get("/orders/{orderId}", 999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("특정 배달 조회 테스트 : 음식점이 특정 주문조회시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_배달_조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food savedFood = foodJpaRepository.save(food);

        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member savedMember = memberJpaRepository.save(member);

        Order order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(savedMember)
                .build();
        order.addOrderLine(OrderLine.builder()
                .order(order)
                .food(savedFood)
                .quantity(2L)
                .build()
        );
        Order savedOrder = orderJpaRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();
        Delivery savedDelivery = deliveryJpaRepository.save(delivery);

        // when, then
        mockMvc.perform(get("/deliveries/{deliveryId}", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 배달 조회 테스트 : 손님이 특정 주문조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_배달_조회_요청() throws Exception {
        // given
        Food food = Food.builder()
                .name("짜장면")
                .foodType("중식")
                .price(9000L)
                .build();
        Food savedFood = foodJpaRepository.save(food);

        Member member = Member.builder()
                .name("name")
                .email("user1@naver.com")
                .password("password")
                .cellPhone("000-0000-0000")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        Member savedMember = memberJpaRepository.save(member);

        Order order = Order.builder()
                .address(new Address("123445", "101동 1001호"))
                .member(savedMember)
                .build();
        order.addOrderLine(OrderLine.builder()
                .order(order)
                .food(savedFood)
                .quantity(2L)
                .build()
        );
        Order savedOrder = orderJpaRepository.save(order);

        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();
        Delivery savedDelivery = deliveryJpaRepository.save(delivery);

        // when, then
        mockMvc.perform(get("/deliveries/{deliveryId}", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}