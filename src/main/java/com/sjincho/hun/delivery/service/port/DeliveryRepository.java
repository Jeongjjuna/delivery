package com.sjincho.hun.delivery.service.port;

import com.sjincho.hun.delivery.domain.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface DeliveryRepository {

    Optional<Delivery> findById(Long id);

    Page<Delivery> findAll(Pageable pageable);

    Delivery save(Delivery delivery);
}
