package com.sjincho.hun.order.service.port;

import com.sjincho.hun.order.domain.OrderLine;
import com.sjincho.hun.order.domain.OrderLines;

public interface OrderLineRepository {

    OrderLines findByOrderId(Long orderId);

    OrderLine save(OrderLine orderLine);
}
