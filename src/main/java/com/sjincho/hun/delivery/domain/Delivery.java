package com.sjincho.hun.delivery.domain;

import com.sjincho.hun.delivery.exception.DeliveryErrorCode;
import com.sjincho.hun.delivery.exception.DeliveryNotDeliveringException;
import com.sjincho.hun.delivery.exception.DeliveryNotReadyStatusException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity(name = "delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "orders_id", nullable = false)
    private Long orderId;

    @Embedded
    private Receiver receiver;

    @Embedded
    private Address address;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "delivery_started_at")
    private LocalDateTime deliveryStartedAt;

    @Builder
    public Delivery(final Long orderId, final Receiver receiver, final Address address, final DeliveryStatus deliveryStatus, final LocalDateTime deliveryStartedAt) {
        this.orderId = orderId;
        this.receiver = receiver;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
        this.deliveryStartedAt = deliveryStartedAt;
    }

    public void start() {
        if (isReadyStatus()) {
            changeDeliveryStatus(DeliveryStatus.DELIVERING);
            deliveryStartedAt = LocalDateTime.now();
            return;
        }
        throw new DeliveryNotReadyStatusException(DeliveryErrorCode.NOT_READY_STATUS);
    }

    public void complete() {
        if (isDeliveringStatus()) {
            changeDeliveryStatus(DeliveryStatus.COMPLETED);
            return;
        }
        throw new DeliveryNotDeliveringException(DeliveryErrorCode.NOT_DELIVERING_STATUS, id);
    }

    private boolean isDeliveringStatus() {
        return deliveryStatus == DeliveryStatus.DELIVERING;
    }

    private boolean isReadyStatus() {
        return deliveryStatus == DeliveryStatus.READY_FOR_DELIVERY;
    }

    private void changeDeliveryStatus(final DeliveryStatus status) {
        deliveryStatus = status;
    }

}
