package com.sjincho.delivery.food.exception;

import com.sjincho.delivery.exception.DeliveryApplicationException;

public class FoodNotFoundException extends DeliveryApplicationException {

    public FoodNotFoundException(final FoodErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("Food Not Found"));
    }

    public FoodNotFoundException(final FoodErrorCode errorCode, final Long id) {
        super(errorCode.getStatus(), errorCode.getMessage(), String.format("id:%d Not Found", id));
    }
}
