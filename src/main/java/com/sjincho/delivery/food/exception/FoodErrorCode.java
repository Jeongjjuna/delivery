package com.sjincho.delivery.food.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FoodErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "[ERROR] Food Not Found");

    private final HttpStatus status;
    private final String message;

    FoodErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
