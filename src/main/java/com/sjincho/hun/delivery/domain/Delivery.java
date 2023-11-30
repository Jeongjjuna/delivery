package com.sjincho.hun.delivery.domain;

import com.sjincho.hun.delivery.exception.DeliveryErrorCode;
import com.sjincho.hun.delivery.exception.DeliveryNotDeliveringException;
import com.sjincho.hun.delivery.exception.DeliveryNotReadyStatusException;
import com.sjincho.hun.order.domain.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @Column(name = "delivery_id", updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "delivery_started_at")
    private LocalDateTime deliveryStartedAt;

    @Builder
    public Delivery(final Order order, final DeliveryStatus deliveryStatus, final LocalDateTime deliveryStartedAt) {
        this.order = order;
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

    public void delete() {
        if (isReadyStatus()) {
            changeDeliveryStatus(DeliveryStatus.CANCELED);
            return;
        }
        throw new DeliveryNotReadyStatusException(DeliveryErrorCode.NOT_READY_STATUS);
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
