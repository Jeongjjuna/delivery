package com.sjincho.delivery.food.controller;

import com.sjincho.delivery.food.dto.FoodResponse;
import com.sjincho.delivery.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(final FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/foods/{id}")
    public FoodResponse get(@PathVariable final Long id) {
        return foodService.get(id);
    }

    @GetMapping("/foods")
    public List<FoodResponse> getAll() {
        return foodService.getAll();
    }
}
