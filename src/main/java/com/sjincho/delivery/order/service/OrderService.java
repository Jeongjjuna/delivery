package com.sjincho.delivery.order.service;

import com.sjincho.delivery.exception.DeliveryApplicationException;
import com.sjincho.delivery.exception.ErrorCode;
import com.sjincho.delivery.order.domain.Order;
import com.sjincho.delivery.order.dto.OrderResponse;
import com.sjincho.delivery.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse get(final Long id) {
        final Order order = findExistingOrder(id);

        return OrderResponse.from(order);
    }

    private Order findExistingOrder(final Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.ORDER_NOT_FOUND, String.format("id:%d Not Found", id)));
    }
}
