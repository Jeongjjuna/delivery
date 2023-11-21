package com.sjincho.hun.delivery.service;

import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.dto.DeliveryResponse;
import com.sjincho.hun.delivery.exception.DeliveryErrorCode;
import com.sjincho.hun.delivery.exception.DeliveryNotFoundException;
import com.sjincho.hun.delivery.service.port.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public DeliveryService(final DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public DeliveryResponse get(final Long deliveryId) {
        final Delivery delivery = findExistingDelivery(deliveryId);

        return DeliveryResponse.from(delivery);
    }

    private Delivery findExistingDelivery(final Long id) {
        return deliveryRepository.findById(id).orElseThrow(() ->
                new DeliveryNotFoundException(DeliveryErrorCode.NOT_FOUND, id));
    }
}
