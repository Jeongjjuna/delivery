package com.sjincho.hun.order.repository;

import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByOrdererMemberId(final Long memberId, final Pageable pageable);

    Page<Order> findAllByOrderStatus(final OrderStatus status, final Pageable pageable);
}
