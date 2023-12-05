package com.sjincho.hun.order.service.port;

import com.sjincho.hun.order.domain.OrderLine;
import java.util.List;

public interface OrderLineRepository {

    List<OrderLine> findByOrderId(Long orderId);
}
