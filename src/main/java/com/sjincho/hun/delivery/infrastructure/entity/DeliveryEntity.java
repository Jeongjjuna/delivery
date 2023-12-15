package com.sjincho.hun.delivery.infrastructure.entity;

import com.sjincho.hun.common.domain.BaseEntity;
import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import com.sjincho.hun.order.infrastructure.entity.OrderEntity;
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
public class DeliveryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id", updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name = "delivery_started_at")
    private LocalDateTime deliveryStartedAt;

    @Builder
    public DeliveryEntity(final OrderEntity orderEntity, final DeliveryStatus deliveryStatus, final LocalDateTime deliveryStartedAt) {
        this.orderEntity = orderEntity;
        this.deliveryStatus = deliveryStatus;
        this.deliveryStartedAt = deliveryStartedAt;
    }

    public static DeliveryEntity from(Delivery delivery) {
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.id = delivery.getId();
        deliveryEntity.orderEntity = OrderEntity.from(delivery.getOrder());
        deliveryEntity.deliveryStatus = delivery.getDeliveryStatus();
        deliveryEntity.deliveryStartedAt = delivery.getDeliveryStartedAt();
        deliveryEntity.createdAt = delivery.getCreatedAt();
        deliveryEntity.updatedAt = delivery.getUpdatedAt();
        deliveryEntity.deletedAt = delivery.getDeletedAt();
        return deliveryEntity;
    }

    public Delivery toModel() {
        return Delivery.builder()
                .id(id)
                .order(orderEntity.toModel())
                .deliveryStatus(deliveryStatus)
                .deliveryStartedAt(deliveryStartedAt)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
