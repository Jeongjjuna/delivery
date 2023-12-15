package com.sjincho.hun.order.domain;

import lombok.Getter;
import java.util.List;

@Getter
public class OrderLines {
    private final List<OrderLine> orderLines;

    public OrderLines(final List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
    public Long getPaymentAmount() {
        return orderLines.stream()
                .map(OrderLine::calculatePayment)
                .mapToLong(Long::longValue)
                .sum();
    }
}
