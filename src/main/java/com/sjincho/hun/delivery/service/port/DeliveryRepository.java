package com.sjincho.hun.delivery.service.port;

import com.sjincho.hun.delivery.domain.Delivery;
import java.util.Optional;

public interface DeliveryRepository {

    Optional<Delivery> findById(Long id);

}
