package com.sjincho.delivery.order.service;

import com.sjincho.delivery.exception.DeliveryApplicationException;
import com.sjincho.delivery.exception.ErrorCode;
import com.sjincho.delivery.food.repository.FoodRepository;
import com.sjincho.delivery.order.domain.Order;
import com.sjincho.delivery.order.domain.OrderLine;
import com.sjincho.delivery.order.dto.OrderAcceptRequest;
import com.sjincho.delivery.order.dto.OrderLineDto;
import com.sjincho.delivery.order.dto.OrderResponse;
import com.sjincho.delivery.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Order findExistingOrder(final Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.ORDER_NOT_FOUND, String.format("id:%d Not Found", id)));
    }

    public List<OrderResponse> getAll() {
        final List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> OrderResponse.from(order, order.calculatePaymentsAmount()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long acceptOrder(final OrderAcceptRequest request) {
        // 주문요청한 모든 음식들이 등록된 음식들인지 확인한다.
        final List<OrderLineDto> orderLineDtos = request.getOrderLineDtos();

        final List<Long> requestFoodIds = orderLineDtos.stream()
                .map(orderLineDto -> orderLineDto.getFoodId())
                .collect(Collectors.toList());

        final List<Long> foodIds = foodRepository.findAll().stream()
                .map(food -> food.getId())
                .collect(Collectors.toList());

        if (!foodIds.containsAll(requestFoodIds)) {
            throw new DeliveryApplicationException(ErrorCode.FOOD_NOT_FOUND, String.format("Order Foods Not Found"));
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
}
