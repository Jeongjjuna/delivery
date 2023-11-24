package com.sjincho.hun.delivery.repository;

import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrderId(Long orderId);

    Page<Delivery> findAllByDeliveryStatus(final DeliveryStatus status, final Pageable pageable);
}
