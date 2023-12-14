package com.sjincho.hun.food.infrastructure;

import com.sjincho.hun.common.domain.BaseEntity;
import com.sjincho.hun.food.domain.Food;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity(name = "food")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at IS NULL")
public class FoodEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "food_type", nullable = false)
    private String foodType;

    @Column(name = "price", nullable = false)
    private Long price;

    public static FoodEntity from(Food food) {
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.id = food.getId();
        foodEntity.name = food.getFoodType();
        foodEntity.foodType = food.getFoodType();
        foodEntity.price = food.getPrice();
        foodEntity.createdAt = food.getCreatedAt();
        foodEntity.updatedAt = food.getUpdatedAt();
        foodEntity.deletedAt = food.getDeletedAt();
        return foodEntity;
    }

    public Food toModel() {
        return Food.builder()
                .id(id)
                .name(name)
                .foodType(foodType)
                .price(price)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
