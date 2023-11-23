package com.sjincho.hun.delivery.dto;

import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class DeliveryResponse {
    private final Long id;
    private final Long orderId;
    private final Long receiverId;
    private final String receiverCellPhone;
    private final String postalCode;
    private final String detailAddress;
    private final DeliveryStatus deliveryStatus;
    private final LocalDateTime deliveryStartedAt;

    @Builder
    public DeliveryResponse(final Long id, final Long orderId,
                            final Long receiverId, final String receiverCellPhone,
                            final String postalCode, final String detailAddress,
                            final DeliveryStatus deliveryStatus, final LocalDateTime deliveryStartedAt) {
        this.id = id;
        this.orderId = orderId;
        this.receiverId = receiverId;
        this.receiverCellPhone = receiverCellPhone;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.deliveryStatus = deliveryStatus;
        this.deliveryStartedAt = deliveryStartedAt;
    }

    public static DeliveryResponse from(final Delivery delivery) {
        return DeliveryResponse.builder()
                .id(delivery.getId())
                .orderId(delivery.getOrderId())
                .receiverId(delivery.getReceiver().getMemberId())
                .receiverCellPhone(delivery.getReceiver().getCellPhone())
                .postalCode(delivery.getAddress().getPostalCode())
                .detailAddress(delivery.getAddress().getDetailAddress())
                .deliveryStatus(delivery.getDeliveryStatus())
                .deliveryStartedAt(delivery.getDeliveryStartedAt())
                .build();
    }
}
