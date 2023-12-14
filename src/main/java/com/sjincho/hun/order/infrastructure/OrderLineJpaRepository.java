package com.sjincho.hun.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderLineJpaRepository extends JpaRepository<OrderLineEntity, Long> {

    @Query("SELECT o "
            + "FROM order_line o "
            + "JOIN FETCH o.foodEntity "
            + "WHERE o.orderEntity.id = :orderId")
    List<OrderLineEntity> findByOrderId(Long orderId);
}
