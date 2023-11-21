package com.sjincho.hun.delivery.repository;

import com.sjincho.hun.delivery.domain.Delivery;
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
    public Page<Delivery> findAll(final Pageable pageable) {
        return deliveryJpaRepository.findAll(pageable);
    }
}
