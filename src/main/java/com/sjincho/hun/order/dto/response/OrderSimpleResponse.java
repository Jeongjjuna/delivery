package com.sjincho.hun.order.dto.response;

import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class OrderSimpleResponse {
    private final Long id;
    private final Long ordererId;
    private final String ordererCellPhone;
    private final String postalCode;
    private final String detailAddress;
    private final LocalDateTime createAt;
    private final OrderStatus orderStatus;

    @Builder
    private OrderSimpleResponse(final Long id, final Long ordererId,
                                final String ordererCellPhone, final String postalCode,
                                final String detailAddress, final LocalDateTime createAt,
                                final OrderStatus orderStatus) {
        this.id = id;
        this.ordererId = ordererId;
        this.ordererCellPhone = ordererCellPhone;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.createAt = createAt;
        this.orderStatus = orderStatus;
    }

    public static OrderSimpleResponse from(final Order order) {
        return OrderSimpleResponse.builder()
                .id(order.getId())
                .ordererId(order.getMember().getId())
                .ordererCellPhone(order.getMember().getCellPhone())
                .postalCode(order.getAddress().getPostalCode())
                .detailAddress(order.getAddress().getDetailAddress())
                .createAt(order.getCreatedAt())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
