package com.sjincho.delivery.order.service;

import com.sjincho.delivery.food.domain.Food;
import com.sjincho.delivery.food.exception.FoodErrorCode;
import com.sjincho.delivery.food.exception.FoodNotFoundException;
import com.sjincho.delivery.food.repository.FoodRepository;
import com.sjincho.delivery.order.domain.Order;
import com.sjincho.delivery.order.domain.OrderLine;
import com.sjincho.delivery.order.domain.OrderStatus;
import com.sjincho.delivery.order.dto.OrderAcceptRequest;
import com.sjincho.delivery.order.dto.OrderLineDto;
import com.sjincho.delivery.order.dto.OrderResponse;
import com.sjincho.delivery.order.exception.OrderErrorCode;
import com.sjincho.delivery.order.exception.OrderNotFoundException;
import com.sjincho.delivery.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public OrderService(final OrderRepository orderRepository, final FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
    }

    public OrderResponse get(final Long id) {
        final Order order = findExistingOrder(id);

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
    public Long order(final OrderAcceptRequest request) {
        // 주문요청한 모든 음식들이 등록된 음식들인지 확인한다.
        final List<OrderLineDto> orderLineDtos = request.getOrderLineDtos();

        final List<Long> requestFoodIds = orderLineDtos.stream()
                .map(OrderLineDto::getFoodId)
                .toList();

        final List<Long> foodIds = foodRepository.findAll().stream()
                .map(Food::getId)
                .toList();

        if (!foodIds.containsAll(requestFoodIds)) {
            throw new FoodNotFoundException(FoodErrorCode.NOT_FOUND);
        }

        // 주문 엔티티(도메인)을 생성한다.
        final List<OrderLine> orderLines = orderLineDtos.stream()
                .map(orderLineDto -> OrderLine.create(
                        orderLineDto.getFoodId(),
                        orderLineDto.getPrice(),
                        orderLineDto.getQuantity(),
                        orderLineDto.getFoodName()))
                .collect(Collectors.toList());

        final Order order = Order.create(
                request.getMemberId(),
                request.getCellPhone(),
                request.getPostalCode(),
                request.getDetailAddress(),
                orderLines
        );

        final Order saved = orderRepository.save(order);

        return saved.getId();
    }

    @Transactional
    public OrderStatus approveOrder(final Long id) {
        final Order order = findExistingOrder(id);

        order.approve();

        orderRepository.save(order);

        return order.getOrderStatus();
    }

    @Transactional
    public OrderStatus rejectOrder(final Long id) {
        final Order order = findExistingOrder(id);

        order.reject();

        orderRepository.save(order);

        return order.getOrderStatus();
    }

    @Transactional
    public OrderStatus cancelOrder(final Long id) {
        final Order order = findExistingOrder(id);

        order.cancel();

        orderRepository.save(order);

        return order.getOrderStatus();
    }

    private Order findExistingOrder(final Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException(OrderErrorCode.NOT_FOUND, id));
    }

}
