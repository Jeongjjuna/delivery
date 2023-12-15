package com.sjincho.hun.delivery.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sjincho.hun.auth.dto.User;
import com.sjincho.hun.config.CustomerMockUser;
import com.sjincho.hun.config.OwnerMockUser;
import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import com.sjincho.hun.delivery.service.port.DeliveryRepository;
import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.service.port.FoodRepository;
import com.sjincho.hun.member.domain.Member;
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
@DisplayName("Delivery 도메인 API 테스트")
class DeliveryControllerTest {

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

    @Autowired
    private DeliveryRepository deliveryRepository;

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

    private Delivery setDelivery(Order savedOrder) {
        Delivery delivery = Delivery.builder()
                .order(savedOrder)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();
        return deliveryRepository.save(delivery);
    }

    @DisplayName("배달 취소 하기 테스트 : 배달준비중이 아닌 배달의 배달취소 요청시 409 응답 반환")
    @Test
    @OwnerMockUser
    void 배달준비중이_아닌_배달에대해_배달_취소_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        savedDelivery.start();
        deliveryRepository.save(savedDelivery);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/cancel", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @DisplayName("배달 취소 하기 테스트 : 손님이 배달취소 요청시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_배달_취소_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/cancel", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("배달 취소 하기 테스트 : 음식점이 배달취소 요청시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_배달_취소_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/cancel", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("배달 완료 하기 테스트 : 배달중이 아닌 배달의 배달완료 요청시 409 응답 반환")
    @Test
    @OwnerMockUser
    void 배달중이_아닌_배달에대해_배달_완료_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/complete", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @DisplayName("배달 완료 하기 테스트 : 손님이 배달완료 요청시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_배달_완료_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/start", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("배달 완료 하기 테스트 : 음식점이 배달완료 요청시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_배달_완료_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        savedDelivery.start();
        deliveryRepository.save(savedDelivery);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/complete", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("배달 시작 하기 테스트 : 준비중인 아닌 배달의 배달시작 요청시 409 응답 반환")
    @Test
    @OwnerMockUser
    void 준비중이_아닌_배달에대해_배달_시작_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        savedDelivery.start();
        deliveryRepository.save(savedDelivery);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/start", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @DisplayName("배달 시작 하기 테스트 : 손님이 배달시작 요청시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_배달_시작_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/start", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("배달 시작 하기 테스트 : 음식점이 배달시작 요청시 200 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_배달_시작_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        // when, then
        mockMvc.perform(patch("/deliveries/{deliveryId}/start", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("배달 등록 하기 테스트 : 존재하지 않는 주문id로 배달등록 요청시 404 응답 반환")
    @Test
    @OwnerMockUser
    void 존재하지_않는_주문의_배달_등록_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(post("/deliveries/orders/{orderId}", 99999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("배달 등록 하기 테스트 : 손님이 배달등록 요청시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_배달_등록_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(post("/deliveries/orders/{orderId}", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @DisplayName("배달 등록 하기 테스트 : 음식점이 배달 등록 요청시 201 응답 반환")
    @Test
    @OwnerMockUser
    void 음식점이_배달_등록_하기() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);

        // when, then
        mockMvc.perform(post("/deliveries/orders/{orderId}", savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("배달중인 주문 목록 조회 테스트 : 손님이 배달중인 주문목록 조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_배달중인_조회_목록조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

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
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        savedDelivery.start();
        deliveryRepository.save(savedDelivery);

        // when, then
        mockMvc.perform(get("/deliveries/in-delivery?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("전체 배달 조회 테스트 : 손님이 전체 배달 조회시 403 응답 반환")
    @Test
    @CustomerMockUser
    void 손님이_전체_배달_조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

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
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        // when, then
        mockMvc.perform(get("/deliveries?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 회원 조회 테스트 : 존재하지 않는 id 배달 조회시 404 응답 반환")
    @Test
    @OwnerMockUser
    void 존재하지_않는_id의_배달_조회_요청() throws Exception {
        // given
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

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
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

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
        Food savedFood = setFood();
        Member savedMember = setMember();
        Order savedOrder = setOrder(savedMember);
        OrderLine savedOrderLine = setOrderLine(savedFood, savedOrder);
        Delivery savedDelivery = setDelivery(savedOrder);

        // when, then
        mockMvc.perform(get("/deliveries/{deliveryId}", savedDelivery.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}