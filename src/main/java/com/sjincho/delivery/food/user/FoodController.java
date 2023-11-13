package com.sjincho.delivery.food.user;

import com.sjincho.delivery.food.FoodResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/foods/{id}")
    public FoodResponseDto get(@PathVariable int id) {
        return foodService.get(id);
    }

    @GetMapping("/foods")
    public List<FoodResponseDto> getAll() {
        return foodService.getAll();
    }
}
