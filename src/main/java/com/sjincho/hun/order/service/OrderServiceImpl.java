package com.sjincho.hun.order.service;

import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.exception.FoodErrorCode;
import com.sjincho.hun.food.exception.FoodNotFoundException;
import com.sjincho.hun.food.service.port.FoodRepository;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.exception.MemberErrorCode;
import com.sjincho.hun.member.exception.MemberNotFoundException;
import com.sjincho.hun.member.service.port.MemberRepository;
import com.sjincho.hun.order.controller.port.OrderService;
import com.sjincho.hun.order.controller.response.OrderDetailResponse;
import com.sjincho.hun.order.controller.response.OrderSimpleResponse;
import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderCreate;
import com.sjincho.hun.order.domain.OrderLine;
import com.sjincho.hun.order.domain.OrderLineCreate;
import com.sjincho.hun.order.domain.OrderStatus;
import com.sjincho.hun.order.exception.OrderErrorCode;
import com.sjincho.hun.order.exception.OrderNotFoundException;
import com.sjincho.hun.order.exception.UnAuthorizedCancelException;
import com.sjincho.hun.order.service.port.OrderLineRepository;
import com.sjincho.hun.order.service.port.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;
    private final OrderLineRepository orderLineRepository;

    public OrderServiceImpl(final OrderRepository orderRepository,
                            final FoodRepository foodRepository,
                            final MemberRepository memberRepository,
                            final OrderLineRepository orderLineRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.memberRepository = memberRepository;
        this.orderLineRepository = orderLineRepository;
    }

    public OrderDetailResponse get(final Long orderId) {
        final Order order = findExistingOrder(orderId);

        List<OrderLine> orderLines = orderLineRepository.findByOrderId(orderId);
        Long paymentAmount = orderLines.stream()
                .map(OrderLine::calculatePayment)
                .mapToLong(Long::longValue)
                .sum();

        return OrderDetailResponse.from(order, orderLines, paymentAmount);
    }

    public Page<OrderSimpleResponse> getAll(final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAllWithMember(pageable);

        return orders.map(order -> OrderSimpleResponse.from(order));
    }

    public Page<OrderSimpleResponse> getAllByMemberId(final Long memberId, final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAllByMemberIdWithMember(memberId, pageable);

        return orders.map(order -> OrderSimpleResponse.from(order));
    }

    public Page<OrderSimpleResponse> getAllAcceptingOrder(final Pageable pageable) {
        final Page<Order> orders = orderRepository.findAllByOrderStatusWithMember(OrderStatus.ACCEPTING, pageable);

        return orders.map(order -> OrderSimpleResponse.from(order));
    }

    @Transactional
    public Long order(final OrderCreate orderCreate, Long ordererId) {
        final List<Long> foodIds = orderCreate.getOrderLineCreates().stream()
                .map(orderLineCreate -> orderLineCreate.getFoodId())
                .toList();
        final List<Food> foods = foodRepository.findAllById(foodIds);
        if (foodIds.size() != foods.size()) {
            throw new FoodNotFoundException(FoodErrorCode.NOT_FOUND);
        }

        final Member orderer = memberRepository.findById(ordererId).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.NOT_FOUND, ordererId));

        Order order = Order.from(orderCreate, orderer);

        final Order savedOrder = orderRepository.save(order);

        orderCreate.getOrderLineCreates().stream()
                .map(orderLineCreate -> toOrderLine(savedOrder, orderLineCreate, foods))
                .forEach(orderLineRepository::save);

        return savedOrder.getId();
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
    public OrderStatus cancelOrder(final Long orderId, Long requesterId) {
        final Order order = findExistingOrder(orderId);

        if (order.getMember().getId() != requesterId) {
            throw new UnAuthorizedCancelException(OrderErrorCode.UNAUTHORIZED_CANCEL, order.getMember().getId(), requesterId);
        }

        order.cancel();

        orderRepository.save(order);

        return order.getOrderStatus();
    }

    private Order findExistingOrder(final Long id) {
        return orderRepository.findByIdWithMember(id).orElseThrow(() ->
                new OrderNotFoundException(OrderErrorCode.NOT_FOUND, id));
    }

    private Food findFoodById(List<Food> foods, Long foodId) {
        return foods.stream()
                .filter(food -> food.getId().equals(foodId))
                .findFirst()
                .orElseThrow(() -> new FoodNotFoundException(FoodErrorCode.NOT_FOUND));
    }

    private OrderLine toOrderLine(Order order, OrderLineCreate orderLineCreate, List<Food> foods) {
        Long foodId = orderLineCreate.getFoodId();
        Food food = findFoodById(foods, foodId);
        return OrderLine.builder()
                .order(order)
                .food(food)
                .quantity(orderLineCreate.getQuantity())
                .build();
    }
}
