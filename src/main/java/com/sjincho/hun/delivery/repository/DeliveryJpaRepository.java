package com.sjincho.hun.delivery.repository;

import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, Long> {

    @Query("SELECT d FROM delivery d JOIN FETCH d.order o JOIN FETCH o.member")
    Page<Delivery> findAllWithOrderWithMember(Pageable pageable);

    @Query("SELECT d FROM delivery d JOIN FETCH d.order o JOIN FETCH o.member WHERE o.id = :orderId")
    Optional<Delivery> findByOrderIdWithOrder(Long orderId);

    @Query("SELECT d FROM delivery d JOIN FETCH d.order o JOIN FETCH o.member WHERE d.deliveryStatus = :status")
    Page<Delivery> findAllByDeliveryStatus(final DeliveryStatus status, final Pageable pageable);
}
