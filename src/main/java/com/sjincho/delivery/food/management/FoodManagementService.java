package com.sjincho.delivery.food.management;

import com.sjincho.delivery.food.Food;
import com.sjincho.delivery.food.FoodRequestDto;
import com.sjincho.delivery.food.FoodMapRepository;
import com.sjincho.delivery.food.FoodResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodManagementService {
    private final FoodMapRepository foodMapRepository;

    @Autowired
    public FoodManagementService(FoodMapRepository foodMapRepository) {
        this.foodMapRepository = foodMapRepository;
    }

    public void register(FoodRequestDto foodRequestDto) {
        Food newFood = foodRequestDto.toEntity();
        foodMapRepository.save(newFood);
    }

    public FoodResponseDto get(Integer foodId) {
        Food food = foodMapRepository.findById(foodId);
        return FoodResponseDto.from(food);
    }

    public List<FoodResponseDto> getAll() {
        List<Food> foods = foodMapRepository.findAll();

        return foods.stream()
                .map(FoodResponseDto::from)
                .collect(Collectors.toList());
    }

    public FoodResponseDto update(int id, Food food) {
        Food updatedFood = foodMapRepository.update(id, food);
        return FoodResponseDto.from(updatedFood);
    }

    public void delete(int id) {
        foodMapRepository.delete(id);
    }
}
