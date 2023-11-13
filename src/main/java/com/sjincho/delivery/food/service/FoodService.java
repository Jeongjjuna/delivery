package com.sjincho.delivery.food.service;

import com.sjincho.delivery.food.repository.FoodMapRepository;
import com.sjincho.delivery.food.dto.FoodResponse;
import com.sjincho.delivery.food.domain.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private final FoodMapRepository foodMapRepository;

    @Autowired
    public FoodService(FoodMapRepository foodMapRepository) {
        this.foodMapRepository = foodMapRepository;
    }

    public FoodResponse get(Integer foodId) {
        Food food = foodMapRepository.findById(foodId);
        return FoodResponse.from(food);
    }

    public List<FoodResponse> getAll() {
        List<Food> foods = foodMapRepository.findAll();

        return foods.stream()
                .map(FoodResponse::from)
                .collect(Collectors.toList());    }
}
