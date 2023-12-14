package com.sjincho.hun.order.controller.response;

import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderLine;
import com.sjincho.hun.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderDetailResponse {
    private final Long id;
    private final List<OrderLineResponse> orderLineResponses;
    private final Long ordererId;
    private final String ordererCellPhone;
    private final String postalCode;
    private final String detailAddress;
    private final LocalDateTime createAt;
    private final Long paymentAmount;
    private final OrderStatus orderStatus;

    @Builder
    private OrderDetailResponse(final Long id, final List<OrderLineResponse> orderLineResponses,
                                final Long ordererId, final String ordererCellPhone,
                                final String postalCode, final String detailAddress,
                                final LocalDateTime createAt, final Long paymentAmount,
                                final OrderStatus orderStatus) {
        this.id = id;
        this.orderLineResponses = orderLineResponses;
        this.ordererId = ordererId;
        this.ordererCellPhone = ordererCellPhone;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.createAt = createAt;
        this.paymentAmount = paymentAmount;
        this.orderStatus = orderStatus;
    }

    public static OrderDetailResponse from(final Order order, final List<OrderLine> orderLines, final Long paymentAmount) {

        final List<OrderLineResponse> orderLineResponses = orderLines.stream()
                .map(OrderLineResponse::from)
                .collect(Collectors.toList());

        return OrderDetailResponse.builder()
                .id(order.getId())
                .orderLineResponses(orderLineResponses)
                .ordererId(order.getMember().getId())
                .ordererCellPhone(order.getMember().getCellPhone())
                .postalCode(order.getAddress().getPostalCode())
                .detailAddress(order.getAddress().getDetailAddress())
                .createAt(order.getCreatedAt())
                .paymentAmount(paymentAmount)
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
