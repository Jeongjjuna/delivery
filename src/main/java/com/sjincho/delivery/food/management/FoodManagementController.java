package com.sjincho.delivery.food.management;

import com.sjincho.delivery.food.Food;
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
public class FoodManagementController {
    private final FoodManagementService foodManagementService;

    @Autowired
    public FoodManagementController(FoodManagementService foodManagementService) {
        this.foodManagementService = foodManagementService;
    }

    @PostMapping("/management/foods")
    public String resister(@RequestBody Food food) {
        foodManagementService.register(food);
        return "200 OK";
    }

    @GetMapping("/management/foods/{id}")
    public Food get(@PathVariable int id) {
        return foodManagementService.get(id);
    }

    @GetMapping("/management/foods")
    public List<Food> getAll() {
        return foodManagementService.getAll();
    }

    @PutMapping("/management/foods/{id}")
    public Food update(@PathVariable int id, @RequestBody Food food) {
        return foodManagementService.update(id, food);
    }

    @DeleteMapping("/management/foods/{id}")
    public String delete(@PathVariable int id) {
        foodManagementService.delete(id);
        return "200 : OK";
    }
}