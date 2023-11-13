package com.sjincho.delivery.admin.food.service;

import com.sjincho.delivery.admin.food.dto.AdminFoodRequest;
import com.sjincho.delivery.admin.food.dto.AdminFoodResponse;
import com.sjincho.delivery.food.domain.Food;
import com.sjincho.delivery.food.repository.FoodMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminFoodService {
    private final FoodMapRepository foodMapRepository;

    @Autowired
    public AdminFoodService(FoodMapRepository foodMapRepository) {
        this.foodMapRepository = foodMapRepository;
    }

    public void register(AdminFoodRequest foodRequest) {
        Food newFood = foodRequest.toEntity();
        foodMapRepository.save(newFood);
    }

    public AdminFoodResponse get(Integer foodId) {
        Food food = foodMapRepository.findById(foodId);
        return AdminFoodResponse.from(food);
    }

    public List<AdminFoodResponse> getAll() {
        List<Food> foods = foodMapRepository.findAll();

        return foods.stream()
                .map(AdminFoodResponse::from)
                .collect(Collectors.toList());
    }

    public AdminFoodResponse update(int id, Food food) {
        Food updatedFood = foodMapRepository.update(id, food);
        return AdminFoodResponse.from(updatedFood);
    }

    public void delete(int id) {
        foodMapRepository.delete(id);
    }
}
