package com.sjincho.hun.delivery.infrastructure;

import com.sjincho.hun.delivery.domain.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity, Long> {

    @Query("SELECT d FROM delivery d JOIN FETCH d.orderEntity o JOIN FETCH o.memberEntity")
    Page<DeliveryEntity> findAllWithOrderWithMember(Pageable pageable);

    @Query("SELECT d FROM delivery d JOIN FETCH d.orderEntity o JOIN FETCH o.memberEntity WHERE o.id = :orderId")
    Optional<DeliveryEntity> findByOrderIdWithOrder(Long orderId);

    @Query("SELECT d FROM delivery d JOIN FETCH d.orderEntity o JOIN FETCH o.memberEntity WHERE d.deliveryStatus = :status")
    Page<DeliveryEntity> findAllByDeliveryStatus(final DeliveryStatus status, final Pageable pageable);
}
