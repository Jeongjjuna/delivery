package com.sjincho.delivery.food.user;

import com.sjincho.delivery.food.Food;
import com.sjincho.delivery.food.FoodMapRepository;
import com.sjincho.delivery.food.FoodResponseDto;
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

    public FoodResponseDto get(Integer foodId) {
        Food food = foodMapRepository.findById(foodId);
        return FoodResponseDto.from(food);
    }

    public List<FoodResponseDto> getAll() {
        List<Food> foods = foodMapRepository.findAll();

        return foods.stream()
                .map(FoodResponseDto::from)
                .collect(Collectors.toList());    }
}
