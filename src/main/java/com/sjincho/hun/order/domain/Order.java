package com.sjincho.hun.order.domain;

import com.sjincho.hun.order.exception.OrderErrorCode;
import com.sjincho.hun.order.exception.OrderNotAcceptingException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "order_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_foods", joinColumns =
    @JoinColumn(name = "order_id")
    )
    private List<OrderLine> orderLines;

    @Embedded
    private Address address;

    @Embedded
    private Orderer orderer;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "create_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order(final List<OrderLine> orderLines, final Address address,
                 final Orderer orderer, final LocalDateTime createdAt,
                 final OrderStatus orderStatus) {
        this.orderLines = orderLines;
        this.address = address;
        this.orderer = orderer;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }

    public static Order create(final Long memberId, final String cellPhone,
                               final String postalCode, final String detailAddress,
                               final List<OrderLine> orderLines) {
        return new Order(
                orderLines,
                new Address(postalCode, detailAddress),
                new Orderer(memberId, cellPhone),
                LocalDateTime.now(),
                OrderStatus.ACCEPTING
        );
    }

    public Long calculatePaymentsAmount() {
        return orderLines.stream()
                .map(OrderLine::calculatePayment)
                .reduce(Long::sum)
                .get();
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
}
