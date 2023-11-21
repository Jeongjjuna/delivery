package com.sjincho.delivery.order.service;

import com.sjincho.delivery.food.domain.Food;
import com.sjincho.delivery.food.exception.FoodErrorCode;
import com.sjincho.delivery.food.exception.FoodNotFoundException;
import com.sjincho.delivery.food.service.port.FoodRepository;
import com.sjincho.delivery.member.domain.Member;
import com.sjincho.delivery.member.exception.MemberErrorCode;
import com.sjincho.delivery.member.exception.MemberNotFoundException;
import com.sjincho.delivery.member.service.port.MemberRepository;
import com.sjincho.delivery.order.domain.Order;
import com.sjincho.delivery.order.domain.OrderLine;
import com.sjincho.delivery.order.domain.OrderStatus;
import com.sjincho.delivery.order.dto.OrderRequest;
import com.sjincho.delivery.order.dto.OrderResponse;
import com.sjincho.delivery.order.exception.OrderErrorCode;
import com.sjincho.delivery.order.exception.OrderNotFoundException;
import com.sjincho.delivery.order.service.port.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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

        List<OrderLine> orderLines = request.getOrderLineRequests().stream()
                .map(orderLineRequest -> {
                    Long foodId = orderLineRequest.getFoodId();
                    Food orderFood = foodRepository.findById(foodId).orElseThrow(() ->
                            new FoodNotFoundException(FoodErrorCode.NOT_FOUND, foodId));
                    return OrderLine.create(
                            orderFood.getId(),
                            orderFood.getPrice(),
                            orderLineRequest.getQuantity(),
                            orderFood.getName()
                    );
                }).toList();

        Member orderer = memberRepository.findById(request.getMemberId()).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.NOT_FOUND, request.getMemberId()));

        final Order order = Order.create(
                orderer.getId(),
                orderer.getCellPhone(),
                request.getPostalCode(),
                request.getDetailAddress(),
                orderLines
        );

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

}
