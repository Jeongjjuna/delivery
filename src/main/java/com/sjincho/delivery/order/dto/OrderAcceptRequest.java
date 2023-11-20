package com.sjincho.delivery.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import java.util.List;

@Getter
public class OrderAcceptRequest {

    @NotNull(message = "주문자 id를 확인해주세요. null 일 수 없습니다.")
    @PositiveOrZero(message = "id 는 음수일 수 없습니다.")
    private final Long memberId;

    @NotBlank(message = "주문자 연락처를 확안해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String cellPhone;

    @NotBlank(message = "우편번호를 확안해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String postalCode;

    @NotBlank(message = "세부주소를 확안해주세요. 빈값 혹은 null 일 수 없습니다.")
    private final String detailAddress;

    @NotEmpty(message = "주문 내용을 확안해주세요. 주문내역이 1개라도 존재해야 합니다.")
    private final List<OrderLineDto> orderLineDtos;

    public OrderAcceptRequest(final Long memberId, final String cellPhone,
                              final String postalCode, final String detailAddress,
                              final List<OrderLineDto> orderLineDtos) {
        this.memberId = memberId;
        this.cellPhone = cellPhone;
        this.postalCode = postalCode;
        this.detailAddress = detailAddress;
        this.orderLineDtos = orderLineDtos;
    }

}
