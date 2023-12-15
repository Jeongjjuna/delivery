package com.sjincho.hun.order.domain;

import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.order.exception.OrderErrorCode;
import com.sjincho.hun.order.exception.OrderNotAcceptingException;
import com.sjincho.hun.order.exception.UnAuthorizedCancelException;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Order {
    private Long id;
    private Member member;
    private Address address;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Order(final Long id, final Member member,
                 final Address address, final OrderStatus orderStatus,
                 final LocalDateTime createdAt, final LocalDateTime updatedAt,
                 final LocalDateTime deletedAt) {
        this.id = id;
        this.member = member;
        this.address = address;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Order from(OrderCreate orderCreate, Member orderer) {
        return Order.builder()
                .member(orderer)
                .address(new Address(
                        orderCreate.getPostalCode(),
                        orderCreate.getDetailAddress())
                )
                .orderStatus(OrderStatus.ACCEPTING)
                .build();
    }

    public void approve() {
        changeOrderStatus(OrderStatus.ACCEPTED);
    }

    public void reject() {
        changeOrderStatus(OrderStatus.REJECTED);
    }

    public void cancel() {
        changeOrderStatus(OrderStatus.CANCELED);
    }

    private boolean isAccepting() {
        return orderStatus == OrderStatus.ACCEPTING;
    }

    private void changeOrderStatus(OrderStatus status) {
        if (isAccepting()) {
            orderStatus = status;
            return;
        }

        throw new OrderNotAcceptingException(OrderErrorCode.NOT_ACCEPTING);
    }

    public void checkSameMember(final Long requesterId) {
        if (member.isSameMember(requesterId)) {
            return;
        }
        throw new UnAuthorizedCancelException(OrderErrorCode.UNAUTHORIZED_CANCEL, member.getId(), requesterId);
    }
}
