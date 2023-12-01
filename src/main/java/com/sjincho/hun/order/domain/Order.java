package com.sjincho.hun.order.domain;

import com.sjincho.hun.common.domain.BaseEntity;
import com.sjincho.hun.member.domain.Member;
import com.sjincho.hun.order.exception.OrderErrorCode;
import com.sjincho.hun.order.exception.OrderNotAcceptingException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", updatable = false)
    private Long id;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderLine> orderLines = new ArrayList<>();

    @Column(name = "create_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    public Order(final Address address, final Member member) {
        this.address = address;
        this.member = member;
        this.orderStatus = OrderStatus.ACCEPTING;
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

    public Long calculatePaymentAmount() {
        return orderLines.stream()
                .map(OrderLine::calculatePayment)
                .mapToLong(Long::longValue)
                .sum();
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }
}
