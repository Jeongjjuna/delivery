package com.sjincho.hun.order.repository;

import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM orders o WHERE o.member.id = :memberId")
    Page<Order> findAllByMemberIdWithMember(final Long memberId, final Pageable pageable);

    Page<Order> findAllByOrderStatus(final OrderStatus status, final Pageable pageable);
}
