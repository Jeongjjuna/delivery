package com.sjincho.delivery.management.food;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FoodMapRepository {
    private Map<Integer, Food> db;
    private Integer idCounter;

    public FoodMapRepository() {
        this.db = new HashMap<>();
        this.idCounter = 0;
    }

    public void save(Food food) {
        db.put(idCounter, food);
        idCounter += 1;
    }

    public Food findById(Integer foodId) {
        return db.get(foodId);
    }

    public List<Food> findAll() {
        List<Food> foodList = new ArrayList<>(db.values());
        return foodList;
    }
}