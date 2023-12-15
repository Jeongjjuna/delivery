package com.sjincho.hun.order.infrastructure;

import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderStatus;
import com.sjincho.hun.order.infrastructure.entity.OrderEntity;
import com.sjincho.hun.order.service.port.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryImpl(final OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Optional<Order> findById(final Long id) {
        return orderJpaRepository.findById(id).map(OrderEntity::toModel);
    }

    @Override
    public Page<Order> findAllWithMember(final Pageable pageable) {
        return orderJpaRepository.findAllWithMember(pageable).map(OrderEntity::toModel);
    }

    @Override
    public Optional<Order> findByIdWithMember(final Long orderId) {
        return orderJpaRepository.findByIdWithMember(orderId).map(OrderEntity::toModel);
    }

    @Override
    public Page<Order> findAllByMemberIdWithMember(final Long memberId, final Pageable pageable) {
        return orderJpaRepository.findAllByMemberIdWithMember(memberId, pageable).map(OrderEntity::toModel);
    }

    @Override
    public Page<Order> findAllByOrderStatusWithMember(final OrderStatus status, final Pageable pageable) {
        return orderJpaRepository.findAllByOrderStatusWithMember(status, pageable).map(OrderEntity::toModel);
    }

    @Override
    public Order save(final Order order) {
        return orderJpaRepository.save(OrderEntity.from(order)).toModel();
    }
}
