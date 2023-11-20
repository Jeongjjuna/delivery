package com.sjincho.delivery.food.controller;

import com.sjincho.delivery.food.dto.FoodCreateRequest;
import com.sjincho.delivery.food.dto.FoodResponse;
import com.sjincho.delivery.food.dto.FoodUpdateRequest;
import com.sjincho.delivery.food.service.FoodService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/foods")
public class FoodController {
    private final FoodService foodService;

    @Autowired
    public FoodController(final FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/{foodId}")
    public ResponseEntity<FoodResponse> get(@PathVariable final Long foodId) {
        final FoodResponse response = foodService.get(foodId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<FoodResponse>> getAll(final Pageable pageable) {
        final Page<FoodResponse> responses = foodService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<Void> resister(@Valid @RequestBody final FoodCreateRequest food) {
        final Long foodId = foodService.register(food);

        return ResponseEntity.created(URI.create("/admin/foods/" + foodId)).build();
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<FoodResponse> update(
            @PathVariable final Long foodId,
            @Valid @RequestBody final FoodUpdateRequest food) {
        final FoodResponse response = foodService.update(foodId, food);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<Void> delete(@PathVariable final Long foodId) {
        foodService.delete(foodId);
        return ResponseEntity.ok().build();
    }
}
