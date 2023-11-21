package com.sjincho.hun.order.service.port;

import com.sjincho.hun.order.domain.Order;
import com.sjincho.hun.order.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findById(Long id);

    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByOrdererMemberId(final Long memberId, final Pageable pageable);

    Page<Order> findAllByOrderStatus(final OrderStatus status, final Pageable pageable);

    Order save(Order order);

}