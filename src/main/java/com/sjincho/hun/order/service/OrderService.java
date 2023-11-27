package com.sjincho.hun.order.service;

import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.exception.FoodErrorCode;
import com.sjincho.hun.food.exception.FoodNotFoundException;
import com.sjincho.hun.food.service.port.FoodRepository;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.exception.MemberErrorCode;
import com.sjincho.hun.member.exception.MemberNotFoundException;
import com.sjincho.hun.member.service.port.MemberRepository;
import com.sjincho.hun.order.domain.Address;
import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderLine;
import com.sjincho.hun.order.domain.OrderStatus;
import com.sjincho.hun.order.dto.request.OrderLineRequest;
import com.sjincho.hun.order.dto.request.OrderRequest;
import com.sjincho.hun.order.dto.response.OrderResponse;
import com.sjincho.hun.order.exception.OrderErrorCode;
import com.sjincho.hun.order.exception.OrderNotFoundException;
import com.sjincho.hun.order.service.port.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;

    public OrderService(final OrderRepository orderRepository,
                        final FoodRepository foodRepository,
                        final MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.memberRepository = memberRepository;
    }

    public OrderResponse get(final Long orderId) {
        final Order order = findExistingOrder(orderId);

        // TODO : 지연로딩 체크 -> 엔티티와 모델을 분리할 생각도 해보자.
        Long paymentAmount = order.calculatePaymentAmount();

        // TODO : 응답에 OrderLine 정보도 넣어주면 좋을 것 같다.
        return OrderResponse.from(order, paymentAmount);
    }

    public Page<OrderResponse> getAll(final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAll(pageable);

        // TODO : order.calculatePaymentAmount() 지연로딩 체크
        return orders.map(order -> OrderResponse.from(order, order.calculatePaymentAmount()));
    }

    public Page<OrderResponse> getAllByMemberId(final Long memberId, final Pageable pageable) {
        // TODO : 지연로딩 체크
        final Page<Order> orders = orderRepository.findAllByMemberIdWithMember(memberId, pageable);

        return orders.map(order -> OrderResponse.from(order, order.calculatePaymentAmount()));
    }

    public Page<OrderResponse> getAllAcceptingOrder(final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAllByOrderStatus(OrderStatus.ACCEPTING, pageable);

        return orders.map(order -> OrderResponse.from(order, order.calculatePaymentAmount()));
    }

    @Transactional
    public Long order(final OrderRequest request) {
        // 요청 음식이 있는지 검사한다.
        final List<Long> foodIds = request.getOrderLineRequests().stream()
                .map(orderLineRequest -> orderLineRequest.getFoodId())
                .toList();
        final List<Food> foods = foodRepository.findAllById(foodIds);
        if (foodIds.size() != foods.size()) {
            throw new FoodNotFoundException(FoodErrorCode.NOT_FOUND);
        }

        // 주문한 사람 가져오기.
        final Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.NOT_FOUND, request.getMemberId()));
        // 오더 생성
        final Order order = Order.builder()
                .address(new Address(request.getPostalCode(), request.getDetailAddress()))
                .member(member)
                .build();

        // 주문 생성하기.
        request.getOrderLineRequests().stream()
                .map(orderLineRequest -> toOrderLine(order, orderLineRequest, foods))
                .toList();

        // 주문 저장 시, List<OrderLine>의 값들도 모두 함께 저장된다.(CascadeType.ALL)
        final Order saved = orderRepository.save(order);

        return saved.getId();
    }

    @Transactional
    public OrderStatus approveOrder(final Long orderId) {
        final Order order = findExistingOrder(orderId);

        order.approve();

        orderRepository.save(order);

        return order.getOrderStatus();
    }

    @Transactional
    public OrderStatus rejectOrder(final Long orderId) {
        final Order order = findExistingOrder(orderId);

        order.reject();

        orderRepository.save(order);

        return order.getOrderStatus();
    }

    @Transactional
    public OrderStatus cancelOrder(final Long orderId) {
        final Order order = findExistingOrder(orderId);

        order.cancel();

        orderRepository.save(order);

        return order.getOrderStatus();
    }

    private Order findExistingOrder(final Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException(OrderErrorCode.NOT_FOUND, id));
    }

    private Food findFoodById(List<Food> foods, Long foodId) {
        return foods.stream()
                .filter(food -> food.getId().equals(foodId))
                .findFirst()
                .orElseThrow(() -> new FoodNotFoundException(FoodErrorCode.NOT_FOUND));
    }

    private OrderLine toOrderLine(Order order, OrderLineRequest orderLineRequest, List<Food> foods) {
        Long foodId = orderLineRequest.getFoodId();
        Food food = findFoodById(foods, foodId);
        return OrderLine.builder()
                .order(order)
                .food(food)
                .quantity(orderLineRequest.getQuantity())
                .build();
    }
}
