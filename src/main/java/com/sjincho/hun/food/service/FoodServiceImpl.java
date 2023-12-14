package com.sjincho.hun.food.service;

import com.sjincho.hun.food.controller.port.FoodService;
import com.sjincho.hun.food.controller.response.FoodResponse;
import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.domain.FoodCreate;
import com.sjincho.hun.food.domain.FoodUpdate;
import com.sjincho.hun.food.exception.FoodErrorCode;
import com.sjincho.hun.food.exception.FoodNotFoundException;
import com.sjincho.hun.food.service.port.FoodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    public FoodServiceImpl(final FoodRepository foodRepository) {
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
    public Long register(final FoodCreate request) {
        final Food food = request.toEntity();

        final Food saved = foodRepository.save(food);

        return saved.getId();
    }

    @Transactional
    public FoodResponse update(final Long foodId, final FoodUpdate foodUpdate) {
        final Food food = findExistingFood(foodId);

        food.update(foodUpdate);

        final Food updatedFood = foodRepository.save(food);

        return FoodResponse.from(updatedFood);
    }

    @Transactional
    public void delete(final Long foodId) {
        final Food food = findExistingFood(foodId);

        food.delete();

        foodRepository.save(food);
    }

    private Food findExistingFood(final Long id) {
        return foodRepository.findById(id).orElseThrow(() ->
                new FoodNotFoundException(FoodErrorCode.NOT_FOUND, id));
    }
}
