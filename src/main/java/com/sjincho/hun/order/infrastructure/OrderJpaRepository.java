package com.sjincho.hun.order.infrastructure;

import com.sjincho.hun.order.domain.OrderStatus;
import com.sjincho.hun.order.infrastructure.entity.OrderEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM orders o JOIN FETCH o.memberEntity")
    Page<OrderEntity> findAllWithMember(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM orders o JOIN FETCH o.memberEntity WHERE o.id = :orderId")
    Optional<OrderEntity> findByIdWithMember(@Param("orderId") Long orderId);

    @Query("SELECT o FROM orders o JOIN FETCH o.memberEntity WHERE o.memberEntity.id = :memberId")
    Page<OrderEntity> findAllByMemberIdWithMember(final Long memberId, final Pageable pageable);

    @Query("SELECT o FROM orders o JOIN FETCH o.memberEntity WHERE o.orderStatus = :status")
    Page<OrderEntity> findAllByOrderStatusWithMember(final OrderStatus status, final Pageable pageable);
}
