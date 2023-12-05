package com.sjincho.hun.order.repository;

import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM orders o JOIN FETCH o.member")
    Page<Order> findAllWithMember(Pageable pageable);

    @Query("SELECT o FROM orders o JOIN FETCH o.member WHERE o.id = :orderId")
    Optional<Order> findByIdWithMember(@Param("orderId") Long orderId);

    @Query("SELECT o FROM orders o JOIN FETCH o.member WHERE o.member.id = :memberId")
    Page<Order> findAllByMemberIdWithMember(final Long memberId, final Pageable pageable);

    @Query("SELECT o FROM orders o JOIN FETCH o.member WHERE o.orderStatus = :status")
    Page<Order> findAllByOrderStatusWithMember(final OrderStatus status, final Pageable pageable);
}
