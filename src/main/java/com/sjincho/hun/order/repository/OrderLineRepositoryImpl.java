package com.sjincho.hun.order.repository;

import com.sjincho.hun.order.domain.OrderLine;
import com.sjincho.hun.order.service.port.OrderLineRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderLineRepositoryImpl implements OrderLineRepository {

    private final OrderLineJpaRepository orderLineJpaRepository;

    public OrderLineRepositoryImpl(final OrderLineJpaRepository orderLineJpaRepository) {
        this.orderLineJpaRepository = orderLineJpaRepository;
    }

    @Override
    public List<OrderLine> findByOrderId(final Long orderId) {
        return orderLineJpaRepository.findByOrderId(orderId);
    }
}
