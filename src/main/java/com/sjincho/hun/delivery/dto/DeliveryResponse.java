package com.sjincho.hun.delivery.dto;

import com.sjincho.hun.delivery.domain.Delivery;
import com.sjincho.hun.delivery.domain.DeliveryStatus;
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
        return new DeliveryResponse(
                delivery.getId(),
                delivery.getOrderId(),
                delivery.getReceiver().getMemberId(),
                delivery.getReceiver().getCellPhone(),
                delivery.getAddress().getPostalCode(),
                delivery.getAddress().getDetailAddress(),
                delivery.getDeliveryStatus(),
                delivery.getDeliveryStartedAt()
        );
    }
}
