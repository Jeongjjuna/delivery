package com.sjincho.hun.food.repository;

import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.service.port.FoodRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class FoodRepositoryImpl implements FoodRepository {
    private final FoodJpaRepository foodJpaRepository;

    public FoodRepositoryImpl(final FoodJpaRepository foodJpaRepository) {
        this.foodJpaRepository = foodJpaRepository;
    }

    @Override
    public Optional<Food> findById(final Long id) {
        return foodJpaRepository.findById(id);
    }

    @Override
    public Page<Food> findAll(final Pageable pageable) {
        return foodJpaRepository.findAll(pageable);
    }

    @Override
    public List<Food> findAllById(final List<Long> Ids) {
        return foodJpaRepository.findAllById(Ids);
    }

    @Override
    public Food save(final Food food) {
        return foodJpaRepository.save(food);
    }

    @Override
    public void delete(final Food food) {
        foodJpaRepository.delete(food);
    }

}
