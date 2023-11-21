package com.sjincho.delivery.order.dto;

import com.sjincho.delivery.order.domain.Order;
import com.sjincho.delivery.order.domain.OrderStatus;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {
    private final Long id;
    private final List<OrderLineResponse> orderLineResponses;
    private final String ordererCellPhone;
    private final String postalCode;
    private final String detailAddress;
    private final LocalDateTime createAt;
    private final Long paymentAmount;
    private final OrderStatus orderStatus;

    private OrderResponse(final Long id, final List<OrderLineResponse> orderLineResponses,
                          final String ordererCellPhone, final String postalCode,
                          final String detailAddress, final LocalDateTime createAt,
                          final Long paymentAmount, final OrderStatus orderStatus) {
        this.id = id;
        this.orderLineResponses = orderLineResponses;
        this.ordererCellPhone = ordererCellPhone;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.createAt = createAt;
        this.paymentAmount = paymentAmount;
        this.orderStatus = orderStatus;
    }

    public static OrderResponse from(final Order order, final Long paymentAmount) {
        final List<OrderLineResponse> orderLineResponses = order.getOrderLines().stream()
                .map(line -> OrderLineResponse.from(
                        line.getFoodId(),
                        line.getPrice(),
                        line.getQuantity(),
                        line.getFoodName()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                orderLineResponses,
                order.getOrderer().getCellPhone(),
                order.getAddress().getPostalCode(),
                order.getAddress().getDetailAddress(),
                order.getCreatedAt(),
                paymentAmount,
                order.getOrderStatus()
        );
    }
}
