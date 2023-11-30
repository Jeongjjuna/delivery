package com.sjincho.hun.delivery.service.port;

import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface DeliveryRepository {

    Optional<Delivery> findById(Long id);

    Page<Delivery> findAll(Pageable pageable);

    Page<Delivery> findAllByDeliveryStatus(final DeliveryStatus deliveryStatus, final Pageable pageable);

    Delivery save(Delivery delivery);

    void delete(Delivery delivery);

    Optional<Delivery> findByOrderIdWithOrder(Long orderId);
}
