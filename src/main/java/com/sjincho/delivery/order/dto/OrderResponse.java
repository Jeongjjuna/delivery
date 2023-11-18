package com.sjincho.delivery.order.dto;

import com.sjincho.delivery.order.domain.Order;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {
    private final Long id;
    private final List<OrderLineDto> orderLineDtos;
    private final String ordererCellPhone;
    private final String postalCode;
    private final String detailAddress;
    private final LocalDateTime createAt;

    private OrderResponse(final Long id, final List<OrderLineDto> orderLineDtos,
                          final String ordererCellPhone, final String postalCode,
                          final String detailAddress, final LocalDateTime createAt) {
        this.id = id;
        this.orderLineDtos = orderLineDtos;
        this.ordererCellPhone = ordererCellPhone;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.createAt = createAt;
    }

    public static OrderResponse from(final Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderLines().stream()
                        .map(line -> OrderLineDto.from(
                                line.getFoodName(),
                                line.getPrice(),
                                line.getQuantity())
                        )
                        .collect(Collectors.toList()),
                order.getOrderer().getCellPhone(),
                order.getAddress().getPostalCode(),
                order.getAddress().getDetailAddress(),
                order.getCreatedAt()
        );
    }
}
