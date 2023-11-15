package com.sjincho.delivery.food.service;

import com.sjincho.delivery.food.domain.Food;
import com.sjincho.delivery.food.dto.FoodResponse;
import com.sjincho.delivery.food.repository.FoodJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private final FoodJpaRepository foodJpaRepository;

    @Autowired
    public FoodService(FoodJpaRepository foodJpaRepository) {
        this.foodJpaRepository = foodJpaRepository;
    }

    public FoodResponse get(Long foodId) {
        final Food food = foodJpaRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] get요청 id에대한 Food데이터가 없습니다."));

        return FoodResponse.from(food);
    }

    public List<FoodResponse> getAll() {
        List<Food> foods = foodJpaRepository.findAll();

        return foods.stream()
                .map(FoodResponse::from)
                .collect(Collectors.toList());
    }
}
