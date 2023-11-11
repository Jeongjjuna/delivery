package com.sjincho.delivery.food.management;

import com.sjincho.delivery.food.Food;
import com.sjincho.delivery.food.FoodMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodManagementService {
    private final FoodMapRepository foodMapRepository;

    @Autowired
    public FoodManagementService(FoodMapRepository foodMapRepository) {
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

    public Food update(int id, Food food) {
        return foodMapRepository.update(id, food);
    }

    public void delete(int id) {
        foodMapRepository.delete(id);
    }
}
