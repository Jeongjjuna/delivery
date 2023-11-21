package com.sjincho.hun.delivery.service;

import com.sjincho.hun.delivery.controller.DeliveryRequest;
import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.dto.DeliveryResponse;
import com.sjincho.hun.delivery.exception.DeliveryErrorCode;
import com.sjincho.hun.delivery.exception.DeliveryNotFoundException;
import com.sjincho.hun.delivery.service.port.DeliveryRepository;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.member.exception.MemberErrorCode;
import com.sjincho.hun.member.exception.MemberNotFoundException;
import com.sjincho.hun.member.service.port.MemberRepository;
import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.exception.OrderErrorCode;
import com.sjincho.hun.order.exception.OrderNotFoundException;
import com.sjincho.hun.order.service.port.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public DeliveryService(final DeliveryRepository deliveryRepository,
                           final OrderRepository orderRepository,
                           final MemberRepository memberRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
    }

    public DeliveryResponse get(final Long deliveryId) {
        final Delivery delivery = findExistingDelivery(deliveryId);

        return DeliveryResponse.from(delivery);
    }

    public Page<DeliveryResponse> getAll(final Pageable pageable) {
        final Page<Delivery> deliveries = deliveryRepository.findAll(pageable);

        return deliveries.map(delivery -> DeliveryResponse.from(delivery));
    }

    @Transactional
    public Long resister(final DeliveryRequest request) {
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                new OrderNotFoundException(OrderErrorCode.NOT_FOUND, request.getOrderId()));

        Member receiver = memberRepository.findById(request.getReceiverId()).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.NOT_FOUND, request.getReceiverId()));

        Delivery delivery = Delivery.create(order, receiver);

        final Delivery saved = deliveryRepository.save(delivery);

        return saved.getId();
    }

    @Transactional
    public void startDelivery(final Long deliveryId) {
        final Delivery delivery = findExistingDelivery(deliveryId);

        delivery.start();

        deliveryRepository.save(delivery);
    }

    private Delivery findExistingDelivery(final Long id) {
        return deliveryRepository.findById(id).orElseThrow(() ->
                new DeliveryNotFoundException(DeliveryErrorCode.NOT_FOUND, id));
    }

    public void delete(final Long deliveryId) {
        final Delivery delivery = findExistingDelivery(deliveryId);

        deliveryRepository.delete(delivery);
    }
}
