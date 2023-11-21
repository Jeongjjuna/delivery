package com.sjincho.delivery.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(DeliveryApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationHandler(final DeliveryApplicationException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(LocalDateTime.now(), e.getErrorCode().getMessage(), e.getMessage()));
    }
}
