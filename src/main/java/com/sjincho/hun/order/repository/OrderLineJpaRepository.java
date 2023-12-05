package com.sjincho.hun.order.repository;

import com.sjincho.hun.order.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderLineJpaRepository extends JpaRepository<OrderLine, Long> {

    @Query("SELECT o "
            + "FROM order_line o "
            + "JOIN FETCH o.food "
            + "WHERE o.order.id = :orderId")
    List<OrderLine> findByOrderId(Long orderId);
}
