package com.sjincho.hun.delivery.domain;

import com.sjincho.hun.delivery.exception.DeliveryErrorCode;
import com.sjincho.hun.delivery.exception.DeliveryNotDeliveringException;
import com.sjincho.hun.delivery.exception.DeliveryNotReadyStatusException;
import com.sjincho.hun.order.domain.Order;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Delivery {
    private Long id;
    private Order order;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime deliveryStartedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Delivery(final Long id, final Order order,
                    final DeliveryStatus deliveryStatus,
                    final LocalDateTime deliveryStartedAt,
                    final LocalDateTime createdAt,
                    final LocalDateTime updatedAt,
                    final LocalDateTime deletedAt) {
        this.id = id;
        this.order = order;
        this.deliveryStatus = deliveryStatus;
        this.deliveryStartedAt = deliveryStartedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Delivery create(final Order order) {
        return Delivery.builder()
                .order(order)
                .deliveryStatus(DeliveryStatus.READY_FOR_DELIVERY)
                .deliveryStartedAt(null)
                .build();
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
