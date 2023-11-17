package com.sjincho.delivery.food.service;

import com.sjincho.delivery.food.dto.FoodCreateRequest;
import com.sjincho.delivery.food.dto.FoodUpdateRequest;
import com.sjincho.delivery.exception.DeliveryApplicationException;
import com.sjincho.delivery.exception.ErrorCode;
import com.sjincho.delivery.food.domain.Food;
import com.sjincho.delivery.food.dto.FoodResponse;
import com.sjincho.delivery.food.repository.FoodJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private final FoodJpaRepository foodJpaRepository;

    @Autowired
    public FoodService(FoodJpaRepository foodJpaRepository) {
        this.foodJpaRepository = foodJpaRepository;
    }

    public FoodResponse get(Long foodId) {
        final Food food = foodJpaRepository.findById(foodId).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.FOOD_NOT_FOUND, String.format("id:%d Not Found", foodId)));

        return FoodResponse.from(food);
    }

    public List<FoodResponse> getAll() {
        List<Food> foods = foodJpaRepository.findAll();

        return foods.stream()
                .map(FoodResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long register(final FoodCreateRequest request) {
        final Food food = Food.create(request.getName(), request.getFoodType(), request.getPrice());

        final Food saved = foodJpaRepository.save(food);

        return saved.getId();
    }

    @Transactional
    public FoodResponse update(final Long foodId, final FoodUpdateRequest request) {
        final Food food = foodJpaRepository.findById(foodId).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.FOOD_NOT_FOUND, String.format("id:%d Not Found", foodId)));

        food.update(request.getName(), request.getFoodType(), request.getPrice());

        final Food updatedFood = foodJpaRepository.save(food);

        return FoodResponse.from(updatedFood);
    }

    @Transactional
    public void delete(final Long foodId) {
        final Food food = foodJpaRepository.findById(foodId).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.FOOD_NOT_FOUND, String.format("id:%d Not Found", foodId)));

        foodJpaRepository.delete(food);
    }
}
