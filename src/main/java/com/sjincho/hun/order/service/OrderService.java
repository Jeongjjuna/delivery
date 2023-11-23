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
import com.sjincho.hun.order.domain.Orderer;
import com.sjincho.hun.order.dto.OrderLineRequest;
import com.sjincho.hun.order.dto.OrderRequest;
import com.sjincho.hun.order.dto.OrderResponse;
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

    public OrderService(final OrderRepository orderRepository, final FoodRepository foodRepository, final MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.memberRepository = memberRepository;
    }

    public OrderResponse get(final Long orderId) {
        final Order order = findExistingOrder(orderId);

        final Long paymentsAmount = order.calculatePaymentsAmount();

        return OrderResponse.from(order, paymentsAmount);
    }

    public Page<OrderResponse> getAll(final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAll(pageable);

        return orders.map(order -> OrderResponse.from(order, order.calculatePaymentsAmount()));
    }

    public Page<OrderResponse> getAllByMemberId(final Long id, final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAllByOrdererMemberId(id, pageable);

        return orders.map(order -> OrderResponse.from(order, order.calculatePaymentsAmount()));
    }

    public Page<OrderResponse> getAllAcceptingOrder(final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAllByOrderStatus(OrderStatus.ACCEPTING, pageable);

        return orders.map(order -> OrderResponse.from(order, order.calculatePaymentsAmount()));
    }

    @Transactional
    public Long order(final OrderRequest request) {

        final List<Long> foodIds = request.getOrderLineRequests().stream()
                .map(orderLineRequest -> orderLineRequest.getFoodId())
                .toList();
        final List<Food> foods = foodRepository.findAllById(foodIds);

        if (foodIds.size() != foods.size()) {
            throw new FoodNotFoundException(FoodErrorCode.NOT_FOUND);
        }

        final Member orderer = memberRepository.findById(request.getMemberId()).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.NOT_FOUND, request.getMemberId()));

        final List<OrderLine> orderLines = request.getOrderLineRequests().stream()
                .map(orderLineRequest -> toOrderLine(orderLineRequest, foods))
                .toList();

        final Order order = Order.builder()
                .orderLines(orderLines)
                .address(new Address(request.getPostalCode(), request.getDetailAddress()))
                .orderer(new Orderer(orderer.getId(), orderer.getCellPhone()))
                .build();

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

    private OrderLine toOrderLine(OrderLineRequest orderLineRequest, List<Food> foods) {
        Long foodId = orderLineRequest.getFoodId();
        Food orderFood = findFoodById(foods, foodId);
        return OrderLine.builder()
                .foodId(orderFood.getId())
                .price(orderFood.getPrice())
                .quantity(orderLineRequest.getQuantity())
                .foodName(orderFood.getName())
                .build();
    }
}
