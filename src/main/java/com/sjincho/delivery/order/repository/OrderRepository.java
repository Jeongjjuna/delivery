package com.sjincho.delivery.order.repository;

import com.sjincho.delivery.order.domain.Order;
import com.sjincho.delivery.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByOrdererMemberId(final Long memberId, final Pageable pageable);

    Page<Order> findAllByOrderStatus(final OrderStatus status, final Pageable pageable);
}
