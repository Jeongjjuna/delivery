package com.sjincho.hun.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class OrderRequest {

    @NotBlank(message = "우편번호를 확안해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String postalCode;

    @NotBlank(message = "세부주소를 확안해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String detailAddress;

    @NotEmpty(message = "주문 내용을 확안해주세요. 주문내역이 1개라도 존재해야 합니다.")
    private final List<OrderLineRequest> orderLineRequests;

    @Builder
    public OrderRequest(final String postalCode,
                        final String detailAddress,
                        final List<OrderLineRequest> orderLineRequests) {
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.orderLineRequests = orderLineRequests;
    }
}
