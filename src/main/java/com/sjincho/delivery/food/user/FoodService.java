package com.sjincho.delivery.food.user;

import com.sjincho.delivery.food.Food;
import com.sjincho.delivery.food.FoodMapRepository;
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

    public Food get(Integer foodId) {
        return foodMapRepository.findById(foodId);
    }

    public List<Food> getAll() {
        return foodMapRepository.findAll();
    }
}
