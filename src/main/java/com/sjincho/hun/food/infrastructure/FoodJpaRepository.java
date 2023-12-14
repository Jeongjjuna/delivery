package com.sjincho.hun.food.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodJpaRepository extends JpaRepository<FoodEntity, Long> {
}
