package com.sjincho.delivery.food.service;

import com.sjincho.delivery.exception.DeliveryApplicationException;
import com.sjincho.delivery.exception.ErrorCode;
import com.sjincho.delivery.food.domain.Food;
import com.sjincho.delivery.food.dto.FoodCreateRequest;
import com.sjincho.delivery.food.dto.FoodResponse;
import com.sjincho.delivery.food.dto.FoodUpdateRequest;
import com.sjincho.delivery.food.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(final FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public FoodResponse get(final Long foodId) {
        final Food food = findExistingFood(foodId);

        return FoodResponse.from(food);
    }

    public List<FoodResponse> getAll() {
        final List<Food> foods = foodRepository.findAll();

        return foods.stream()
                .map(FoodResponse::from)
                .collect(Collectors.toList());
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
                new DeliveryApplicationException(ErrorCode.FOOD_NOT_FOUND, String.format("id:%d Not Found", id)));
    }
}
