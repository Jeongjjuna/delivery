package com.sjincho.delivery.admin.food.service;

import com.sjincho.delivery.admin.food.dto.AdminFoodCreateRequest;
import com.sjincho.delivery.admin.food.dto.AdminFoodResponse;
import com.sjincho.delivery.admin.food.dto.AdminFoodUpdateRequest;
import com.sjincho.delivery.admin.food.repository.AdminFoodJpaRepository;
import com.sjincho.delivery.exception.DeliveryApplicationException;
import com.sjincho.delivery.exception.ErrorCode;
import com.sjincho.delivery.food.domain.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminFoodService {
    private final AdminFoodJpaRepository adminFoodJpaRepository;

    @Autowired
    public AdminFoodService(final AdminFoodJpaRepository adminFoodJpaRepository) {
        this.adminFoodJpaRepository = adminFoodJpaRepository;
    }

    public Long register(final AdminFoodCreateRequest request) {
        final Food food = Food.create(request.getName(), request.getFoodType(), request.getPrice());

        final Food saved = adminFoodJpaRepository.save(food);

        return saved.getId();
    }

    public AdminFoodResponse get(final Long foodId) {
        final Food food = adminFoodJpaRepository.findById(foodId).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.FOOD_NOT_FOUND, String.format("id:%d Not Found", foodId)));

        return AdminFoodResponse.from(food);
    }

    public List<AdminFoodResponse> getAll() {
        final List<Food> foods = adminFoodJpaRepository.findAll();

        return foods.stream()
                .map(AdminFoodResponse::from)
                .collect(Collectors.toList());
    }

    public AdminFoodResponse update(final Long foodId, final AdminFoodUpdateRequest request) {
        final Food food = adminFoodJpaRepository.findById(foodId).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.FOOD_NOT_FOUND, String.format("id:%d Not Found", foodId)));

        food.update(request.getName(), request.getFoodType(), request.getPrice());

        final Food updatedFood = adminFoodJpaRepository.save(food);

        return AdminFoodResponse.from(updatedFood);
    }

    public void delete(final Long foodId) {
        final Food food = adminFoodJpaRepository.findById(foodId).orElseThrow(() ->
                new DeliveryApplicationException(ErrorCode.FOOD_NOT_FOUND, String.format("id:%d Not Found", foodId)));

        adminFoodJpaRepository.delete(food);
    }
}
