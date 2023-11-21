package com.sjincho.hun.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orderer {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "orderer_cellPhone", nullable = false)
    private String cellPhone;

    public Orderer(final Long memberId, final String cellPhone) {
        this.memberId = memberId;
        this.cellPhone = cellPhone;
    }
}