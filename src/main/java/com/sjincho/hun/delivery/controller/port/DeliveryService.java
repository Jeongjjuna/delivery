package com.sjincho.hun.delivery.controller.port;

import com.sjincho.hun.delivery.controller.response.DeliveryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {

    DeliveryResponse get(final Long deliveryId);

    Page<DeliveryResponse> getAll(final Pageable pageable);

    Page<DeliveryResponse> getAllDelivering(final Pageable pageable);

    Long resister(final Long orderId);

    void startDelivery(final Long deliveryId);

    void completeDelivery(final Long deliveryId);

    void cancelDelivery(final Long deliveryId);
}
