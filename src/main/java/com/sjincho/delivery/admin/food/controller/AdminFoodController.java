package com.sjincho.delivery.admin.food.controller;

import com.sjincho.delivery.admin.food.dto.AdminFoodRequest;
import com.sjincho.delivery.admin.food.dto.AdminFoodResponse;
import com.sjincho.delivery.admin.food.service.AdminFoodService;
import com.sjincho.delivery.food.domain.Food;
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
public class AdminFoodController {
    private final AdminFoodService adminFoodService;

    @Autowired
    public AdminFoodController(AdminFoodService adminFoodService) {
        this.adminFoodService = adminFoodService;
    }

    @PostMapping("/admin/foods")
    public String resister(@RequestBody AdminFoodRequest food) {
        adminFoodService.register(food);
        return "200 OK";
    }

    @GetMapping("/admin/foods/{id}")
    public AdminFoodResponse get(@PathVariable int id) {
        return adminFoodService.get(id);
    }

    @GetMapping("/admin/foods")
    public List<AdminFoodResponse> getAll() {
        return adminFoodService.getAll();
    }

    @PutMapping("/admin/foods/{id}")
    public AdminFoodResponse update(@PathVariable int id, @RequestBody Food food) {
        return adminFoodService.update(id, food);
    }

    @DeleteMapping("/admin/foods/{id}")
    public String delete(@PathVariable int id) {
        adminFoodService.delete(id);
        return "200 : OK";
    }
}
