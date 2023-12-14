package com.sjincho.hun.food.controller.port;

import com.sjincho.hun.food.controller.response.FoodResponse;
import com.sjincho.hun.food.domain.FoodCreate;
import com.sjincho.hun.food.domain.FoodUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodService {

    FoodResponse get(Long foodId);

    Page<FoodResponse> getAll(Pageable pageable);
    Long register(final FoodCreate request);

    FoodResponse update(final Long foodId, final FoodUpdate request);

    void delete(final Long foodId);

}