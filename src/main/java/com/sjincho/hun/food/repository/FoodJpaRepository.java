package com.sjincho.hun.food.repository;

import com.sjincho.hun.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodJpaRepository extends JpaRepository<Food, Long> {
}
