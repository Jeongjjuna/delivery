package com.sjincho.hun.delivery.repository;

import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import com.sjincho.hun.delivery.service.port.DeliveryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class DeliveryRepositoryImpl implements DeliveryRepository {
    private final DeliveryJpaRepository deliveryJpaRepository;

    public DeliveryRepositoryImpl(final DeliveryJpaRepository deliveryJpaRepository) {
        this.deliveryJpaRepository = deliveryJpaRepository;
    }

    @Override
    public Optional<Delivery> findById(final Long id) {
        return deliveryJpaRepository.findById(id);
    }

    @Override
    public Optional<Delivery> findByOrderIdWithOrder(final Long orderId) {
        return deliveryJpaRepository.findByOrderIdWithOrder(orderId);
    }

    @Override
    public Page<Delivery> findAll(final Pageable pageable) {
        return deliveryJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Delivery> findAllByDeliveryStatus(final DeliveryStatus deliveryStatus, final Pageable pageable) {
        return deliveryJpaRepository.findAllByDeliveryStatus(deliveryStatus, pageable);
    }

    @Override
    public Delivery save(final Delivery delivery) {
        return deliveryJpaRepository.save(delivery);
    }

    @Override
    public void delete(final Delivery delivery) {
        deliveryJpaRepository.delete(delivery);
    }

}
