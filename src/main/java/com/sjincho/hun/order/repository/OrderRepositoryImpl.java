package com.sjincho.hun.order.repository;

import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderStatus;
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
    public Page<Order> findAll(final Pageable pageable) {
        return orderJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> findById(final Long id) {
        return orderJpaRepository.findById(id);
    }

    @Override
    public Page<Order> findAllByMemberIdWithMember(final Long memberId, final Pageable pageable) {
        return orderJpaRepository.findAllByMemberIdWithMember(memberId, pageable);
    }

    @Override
    public Page<Order> findAllByOrderStatus(final OrderStatus status, final Pageable pageable) {
        return orderJpaRepository.findAllByOrderStatus(status, pageable);
    }

    @Override
    public Order save(final Order order) {
        return orderJpaRepository.save(order);
    }
}
