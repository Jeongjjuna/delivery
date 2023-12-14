package com.sjincho.hun.food.infrastructure;

import com.sjincho.hun.food.domain.Food;
import com.sjincho.hun.food.exception.FoodErrorCode;
import com.sjincho.hun.food.exception.FoodNotFoundException;
import org.springframework.stereotype.Repository;
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

    public void save(final Food food) {
        // food.setId(idCounter);
        db.put(idCounter, food);
        idCounter += 1;
    }

    public Food findById(final Long foodId) {
        return db.get(foodId);
    }

    public List<Food> findAll() {
        final List<Food> foodEntities = List.copyOf(db.values());
        return foodEntities;
    }

    public Food update(final Long id, final Food food) {
        if (db.containsKey(id)) {
            // food.setId(id);
            db.put(id, food);
            return food;
        }
        throw new FoodNotFoundException(FoodErrorCode.NOT_FOUND);
    }

    public void delete(final Long id) {
        db.remove(id);
    }
}