package com.sjincho.hun.delivery.domain;

import com.sjincho.hun.delivery.dto.Receiver;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.order.domain.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
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

    public Delivery(final Long orderId, final Receiver receiver, final Address address, final DeliveryStatus deliveryStatus, final LocalDateTime deliveryStartedAt) {
        this.orderId = orderId;
        this.receiver = receiver;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
        this.deliveryStartedAt = deliveryStartedAt;
    }

    public static Delivery create(final Order order, final Member receiver) {
        return new Delivery(
                order.getId(),
                new Receiver(receiver.getId(), receiver.getCellPhone()),
                new Address(order.getAddress().getPostalCode(), order.getAddress().getDetailAddress()),
                DeliveryStatus.READY_FOR_DELIVERY,
                null
        );
    }
}
