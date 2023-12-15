package com.sjincho.hun.order.infrastructure;

import com.sjincho.hun.order.domain.OrderLine;
import com.sjincho.hun.order.domain.OrderLines;
import com.sjincho.hun.order.infrastructure.entity.OrderLineEntity;
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
    public OrderLines findByOrderId(final Long orderId) {
        List<OrderLine> orderLines = orderLineJpaRepository.findByOrderId(orderId).stream()
                .map(OrderLineEntity::toModel)
                .toList();
        return new OrderLines(orderLines);
    }

    @Override
    public OrderLine save(final OrderLine orderLine) {
        return orderLineJpaRepository.save(OrderLineEntity.from(orderLine)).toModel();
    }
}
