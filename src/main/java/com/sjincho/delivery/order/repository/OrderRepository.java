package com.sjincho.delivery.order.repository;

import com.sjincho.delivery.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
