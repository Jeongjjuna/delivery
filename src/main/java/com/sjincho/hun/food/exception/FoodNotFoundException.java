package com.sjincho.hun.food.exception;

import com.sjincho.hun.exception.DeliveryApplicationException;

public class FoodNotFoundException extends DeliveryApplicationException {

    public FoodNotFoundException(final FoodErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("Food not found"));
    }

    public FoodNotFoundException(final FoodErrorCode errorCode, final Long id) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%d not found", id));
    }
}
