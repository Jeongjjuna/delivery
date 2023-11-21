package com.sjincho.delivery.food.service;

import com.sjincho.delivery.food.domain.Food;
import com.sjincho.delivery.food.dto.FoodCreateRequest;
import com.sjincho.delivery.food.dto.FoodResponse;
import com.sjincho.delivery.food.dto.FoodUpdateRequest;
import com.sjincho.delivery.food.exception.FoodErrorCode;
import com.sjincho.delivery.food.exception.FoodNotFoundException;
import com.sjincho.delivery.food.service.port.FoodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(final FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public FoodResponse get(final Long foodId) {
        final Food food = findExistingFood(foodId);

        return FoodResponse.from(food);
    }

    public Page<FoodResponse> getAll(final Pageable pageable) {
        final Page<Food> foods = foodRepository.findAll(pageable);

        return foods.map(FoodResponse::from);
    }

    @Transactional
    public Long register(final FoodCreateRequest request) {
        final Food food = Food.create(request.getName(), request.getFoodType(), request.getPrice());

        final Food saved = foodRepository.save(food);

        return saved.getId();
    }

    @Transactional
    public FoodResponse update(final Long foodId, final FoodUpdateRequest request) {
        final Food food = findExistingFood(foodId);

        food.update(request.getName(), request.getFoodType(), request.getPrice());

        final Food updatedFood = foodRepository.save(food);

        return FoodResponse.from(updatedFood);
    }

    @Transactional
    public void delete(final Long foodId) {
        final Food food = findExistingFood(foodId);

        foodRepository.delete(food);
    }

    private Food findExistingFood(final Long id) {
        return foodRepository.findById(id).orElseThrow(() ->
                new FoodNotFoundException(FoodErrorCode.NOT_FOUND, id));
    }
}
