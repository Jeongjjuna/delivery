package com.sjincho.hun.food.service.port;

import com.sjincho.hun.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface FoodRepository {

    Optional<Food> findById(Long id);

    Page<Food> findAll(Pageable pageable);

    List<Food> findAllById(List<Long> foodIds);

    Food save(Food food);
}
