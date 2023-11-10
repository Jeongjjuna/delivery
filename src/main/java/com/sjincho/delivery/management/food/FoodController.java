package com.sjincho.delivery.management.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @PostMapping("/management/foods")
    public String resister(@RequestBody Food food) {
        foodService.register(food);
        return "200 OK";
    }

    @GetMapping("/management/foods/{id}")
    public Food get(@PathVariable int id) {
        return foodService.get(id);
    }

    @GetMapping("/management/foods")
    public List<Food> getAll() {
        return foodService.getAll();
    }

    @PutMapping("/management/foods/{id}")
    public Food update(@PathVariable int id, @RequestBody Food food) {
        return foodService.update(id, food);
    }

    @DeleteMapping("/management/foods/{id}")
    public String delete(@PathVariable int id) {
        foodService.delete(id);
        return "200 : OK";
    }
}
