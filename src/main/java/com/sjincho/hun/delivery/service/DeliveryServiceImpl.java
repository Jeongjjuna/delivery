package com.sjincho.hun.delivery.service;

import com.sjincho.hun.delivery.controller.port.DeliveryService;
import com.sjincho.hun.delivery.controller.response.DeliveryResponse;
import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import com.sjincho.hun.delivery.exception.DeliveryAlreadyRegisterException;
import com.sjincho.hun.delivery.exception.DeliveryErrorCode;
import com.sjincho.hun.delivery.exception.DeliveryNotFoundException;
import com.sjincho.hun.delivery.service.port.DeliveryRepository;
import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.exception.OrderErrorCode;
import com.sjincho.hun.order.exception.OrderNotFoundException;
import com.sjincho.hun.order.service.port.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    public DeliveryServiceImpl(final DeliveryRepository deliveryRepository, final OrderRepository orderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
    }

    public DeliveryResponse get(final Long deliveryId) {
        final Delivery delivery = findExistingDelivery(deliveryId);

        return DeliveryResponse.from(delivery);
    }

    public Page<DeliveryResponse> getAll(final Pageable pageable) {
        final Page<Delivery> deliveries = deliveryRepository.findAllWithOrderWithMember(pageable);

        return deliveries.map(DeliveryResponse::from);
    }

    public Page<DeliveryResponse> getAllDelivering(final Pageable pageable) {
        final Page<Delivery> deliveries = deliveryRepository.findAllByDeliveryStatus(
                DeliveryStatus.DELIVERING, pageable);

        return deliveries.map(DeliveryResponse::from);
    }

    @Transactional
    public Long resister(final Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(OrderErrorCode.NOT_FOUND, orderId));

        deliveryRepository.findByOrderIdWithOrder(order.getId())
                .ifPresent(delivery -> {
                    throw new DeliveryAlreadyRegisterException(DeliveryErrorCode.ALREADY_REGISTERED, order.getId());
                });

        final Delivery delivery = Delivery.create(order);

        final Delivery saved = deliveryRepository.save(delivery);

        return saved.getId();
    }

    @Transactional
    public void startDelivery(final Long deliveryId) {
        final Delivery delivery = findExistingDelivery(deliveryId);

        delivery.start();

        deliveryRepository.save(delivery);
    }

    @Transactional
    public void completeDelivery(final Long deliveryId) {
        final Delivery delivery = findExistingDelivery(deliveryId);

        delivery.complete();

        deliveryRepository.save(delivery);
    }

    @Transactional
    public void cancelDelivery(final Long deliveryId) {
        final Delivery delivery = findExistingDelivery(deliveryId);

        delivery.delete();

        deliveryRepository.save(delivery);
    }

    private Delivery findExistingDelivery(final Long id) {
        return deliveryRepository.findById(id).orElseThrow(() ->
                new DeliveryNotFoundException(DeliveryErrorCode.NOT_FOUND, id));
    }
}
