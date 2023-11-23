package com.sjincho.hun.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class OrderRequest {

    @NotNull(message = "주문자 id를 확인해주세요. null 일 수 없습니다.")
    @PositiveOrZero(message = "id 는 음수일 수 없습니다.")
    private final Long memberId;

    @NotBlank(message = "우편번호를 확안해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String postalCode;

    @NotBlank(message = "세부주소를 확안해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String detailAddress;

    @NotEmpty(message = "주문 내용을 확안해주세요. 주문내역이 1개라도 존재해야 합니다.")
    private final List<OrderLineRequest> orderLineRequests;

    @Builder
    public OrderRequest(final Long memberId, final String postalCode,
                        final String detailAddress, final List<OrderLineRequest> orderLineRequests) {
        this.memberId = memberId;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.orderLineRequests = orderLineRequests;
    }
}
