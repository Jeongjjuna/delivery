package com.sjincho.hun.delivery.repository;

import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, Long> {

    @Query("SELECT d FROM delivery d WHERE d.order.id = :orderId")
    Optional<Delivery> findByOrderIdWithOrder(Long orderId);

    Page<Delivery> findAllByDeliveryStatus(final DeliveryStatus status, final Pageable pageable);
}
