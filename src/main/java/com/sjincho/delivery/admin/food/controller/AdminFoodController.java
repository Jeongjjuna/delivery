package com.sjincho.delivery.admin.food.controller;

import com.sjincho.delivery.admin.food.dto.AdminFoodCreateRequest;
import com.sjincho.delivery.admin.food.dto.AdminFoodResponse;
import com.sjincho.delivery.admin.food.dto.AdminFoodUpdateRequest;
import com.sjincho.delivery.admin.food.service.AdminFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

@RestController
public class AdminFoodController {
    private final AdminFoodService adminFoodService;

    @Autowired
    public AdminFoodController(final AdminFoodService adminFoodService) {
        this.adminFoodService = adminFoodService;
    }

    @PostMapping("/admin/foods")
    public ResponseEntity<Void> resister(@RequestBody final AdminFoodCreateRequest food) {
        final Long foodId = adminFoodService.register(food);

        return ResponseEntity.created(URI.create("/admin/foods/" + foodId)).build();
    }

    @GetMapping("/admin/foods/{id}")
    public ResponseEntity<AdminFoodResponse> get(@PathVariable final Long id) {
        final AdminFoodResponse response = adminFoodService.get(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/foods")
    public ResponseEntity<List<AdminFoodResponse>> getAll() {
        final List<AdminFoodResponse> responses = adminFoodService.getAll();

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/admin/foods/{id}")
    public ResponseEntity<AdminFoodResponse> update(
            @PathVariable final Long id,
            @RequestBody final AdminFoodUpdateRequest food) {
        final AdminFoodResponse response = adminFoodService.update(id, food);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/foods/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminFoodService.delete(id);
        return ResponseEntity.ok().build();
    }
}
