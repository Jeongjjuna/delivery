package com.sjincho.delivery.exception;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final String message;
    private final String details;

    public ErrorResponse(final LocalDateTime timestamp, final String message, final String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
