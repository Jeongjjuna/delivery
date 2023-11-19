package com.sjincho.delivery.food.controller;

import com.sjincho.delivery.food.dto.FoodCreateRequest;
import com.sjincho.delivery.food.dto.FoodResponse;
import com.sjincho.delivery.food.dto.FoodUpdateRequest;
import com.sjincho.delivery.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(final FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/foods/{id}")
    public ResponseEntity<FoodResponse> get(@PathVariable final Long id) {
        final FoodResponse response = foodService.get(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/foods")
    public ResponseEntity<Page<FoodResponse>> getAll(Pageable pageable) {
        final Page<FoodResponse> responses = foodService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/foods")
    public ResponseEntity<Void> resister(@RequestBody final FoodCreateRequest food) {
        final Long foodId = foodService.register(food);

        return ResponseEntity.created(URI.create("/admin/foods/" + foodId)).build();
    }

    @PutMapping("/foods/{id}")
    public ResponseEntity<FoodResponse> update(
            @PathVariable final Long id,
            @RequestBody final FoodUpdateRequest food) {
        final FoodResponse response = foodService.update(id, food);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/foods/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        foodService.delete(id);
        return ResponseEntity.ok().build();
    }
}
