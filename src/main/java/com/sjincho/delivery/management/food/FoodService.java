package com.sjincho.delivery.management.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodService {
    private final FoodMapRepository foodMapRepository;

    @Autowired
    public FoodService(FoodMapRepository foodMapRepository) {
        this.foodMapRepository = foodMapRepository;
    }

    public void register(Food food) {
        foodMapRepository.save(food);
    }

    public Food get(Integer foodId) {
        return foodMapRepository.findById(foodId);
    }

    public List<Food> getAll() {
        return foodMapRepository.findAll();
    }
}
