package com.sjincho.hun.order.infrastructure.entity;

import com.sjincho.hun.common.domain.BaseEntity;
import com.sjincho.hun.member.infastructure.entity.MemberEntity;
import com.sjincho.hun.order.domain.Address;
import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "detail_address", nullable = false)
    private String detailAddress;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public static OrderEntity from(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.id = order.getId();
        orderEntity.postalCode = order.getAddress().getPostalCode();
        orderEntity.detailAddress = order.getAddress().getDetailAddress();
        orderEntity.memberEntity = MemberEntity.from(order.getMember());
        orderEntity.orderStatus = order.getOrderStatus();
        orderEntity.createdAt = order.getCreatedAt();
        orderEntity.updatedAt = order.getUpdatedAt();
        orderEntity.deletedAt = order.getDeletedAt();
        return orderEntity;
    }

    public Order toModel() {
        return Order.builder()
                .id(id)
                .member(memberEntity.toModel())
                .address(new Address(postalCode, detailAddress))
                .orderStatus(orderStatus)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
