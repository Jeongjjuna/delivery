package com.sjincho.delivery.order.repository;

import com.sjincho.delivery.order.domain.Order;
import com.sjincho.delivery.order.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrdererMemberId(Long memberId);

    List<Order> findAllByOrderStatus(OrderStatus accepting);
}
