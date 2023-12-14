package com.sjincho.hun.order.controller.port;

import com.sjincho.hun.order.controller.response.OrderDetailResponse;
import com.sjincho.hun.order.controller.response.OrderSimpleResponse;
import com.sjincho.hun.order.domain.OrderCreate;
import com.sjincho.hun.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDetailResponse get(final Long orderId);

    Page<OrderSimpleResponse> getAll(final Pageable pageable);

    Page<OrderSimpleResponse> getAllByMemberId(final Long memberId, final Pageable pageable);

    Page<OrderSimpleResponse> getAllAcceptingOrder(final Pageable pageable);

    Long order(final OrderCreate orderCreate, Long ordererId);

    OrderStatus approveOrder(final Long orderId);

    OrderStatus rejectOrder(final Long orderId);

    OrderStatus cancelOrder(final Long orderId, Long requesterId);

}
