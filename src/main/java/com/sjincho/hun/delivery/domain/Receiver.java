package com.sjincho.hun.delivery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receiver {

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "receiver_cellPhone", nullable = false)
    private String cellPhone;

    @Builder
    public Receiver(final Long memberId, final String cellPhone) {
        this.memberId = memberId;
        this.cellPhone = cellPhone;
    }
}
