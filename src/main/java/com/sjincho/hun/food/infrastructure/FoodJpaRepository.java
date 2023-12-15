package com.sjincho.hun.food.infrastructure;

import com.sjincho.hun.food.infrastructure.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodJpaRepository extends JpaRepository<FoodEntity, Long> {
}
