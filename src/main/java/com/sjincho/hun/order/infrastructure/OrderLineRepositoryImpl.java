package com.sjincho.hun.order.infrastructure;

import com.sjincho.hun.order.domain.OrderLine;
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
    public List<OrderLine> findByOrderId(final Long orderId) {
        return orderLineJpaRepository.findByOrderId(orderId).stream()
                .map(OrderLineEntity::toModel)
                .toList();
    }

    @Override
    public OrderLine save(final OrderLine orderLine) {
        return orderLineJpaRepository.save(OrderLineEntity.from(orderLine)).toModel();
    }
}
