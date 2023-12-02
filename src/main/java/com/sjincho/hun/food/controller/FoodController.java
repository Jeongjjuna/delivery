package com.sjincho.hun.food.controller;

import com.sjincho.hun.food.dto.FoodCreateRequest;
import com.sjincho.hun.food.dto.FoodResponse;
import com.sjincho.hun.food.dto.FoodUpdateRequest;
import com.sjincho.hun.food.service.FoodService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "food-controller", description = "음식상품 서비스")
public class FoodController {
    private final FoodService foodService;

    public FoodController(final FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/{foodId}")
    @PreAuthorize("hasAnyAuthority('customer', 'owner')")
    public ResponseEntity<FoodResponse> get(@PathVariable final Long foodId) {
        final FoodResponse response = foodService.get(foodId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('customer', 'owner')")
    public ResponseEntity<Page<FoodResponse>> getAll(final Pageable pageable) {
        final Page<FoodResponse> responses = foodService.getAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> resister(@Valid @RequestBody final FoodCreateRequest food) {
        final Long foodId = foodService.register(food);

        return ResponseEntity.created(URI.create("/foods/" + foodId)).build();
    }

    @PutMapping("/{foodId}")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<FoodResponse> update(
            @PathVariable final Long foodId,
            @Valid @RequestBody final FoodUpdateRequest food) {
        final FoodResponse response = foodService.update(foodId, food);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{foodId}")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<Void> delete(@PathVariable final Long foodId) {
        foodService.delete(foodId);
        return ResponseEntity.ok().build();
    }
}
