package com.sjincho.delivery.food.repository;

import com.sjincho.delivery.food.domain.Food;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FoodMapRepository {
    private Map<Long, Food> db;
    private Long idCounter;

    public FoodMapRepository() {
        this.db = new HashMap<>();
        this.idCounter = 0L;
    }

    public void save(Food food) {
        food.setId(idCounter);
        db.put(idCounter, food);
        idCounter += 1;
    }

    public Food findById(Long foodId) {
        return db.get(foodId);
    }

    public List<Food> findAll() {
        List<Food> foodList = new ArrayList<>(db.values());
        return foodList;
    }

    public Food update(Long id, Food food) {
        if (db.containsKey(id)) {
            food.setId(id);
            db.put(id, food);
            return food;
        }
        throw new IllegalArgumentException("수정하려는 Food 상품이 없음");
    }

    public void delete(Long id) {
        db.remove(id);
    }
}