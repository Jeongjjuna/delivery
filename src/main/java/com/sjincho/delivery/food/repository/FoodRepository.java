package com.sjincho.delivery.food.repository;

import com.sjincho.delivery.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
