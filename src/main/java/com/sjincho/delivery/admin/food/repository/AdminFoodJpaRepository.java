package com.sjincho.delivery.admin.food.repository;

import com.sjincho.delivery.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminFoodJpaRepository extends JpaRepository<Food, Long> {
}
