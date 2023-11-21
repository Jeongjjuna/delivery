package com.sjincho.delivery.food.service.port;

import com.sjincho.delivery.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface FoodRepository {

    Optional<Food> findById(Long id);

    Page<Food> findAll(Pageable pageable);

    Food save(Food food);

    void delete(Food food);
}
