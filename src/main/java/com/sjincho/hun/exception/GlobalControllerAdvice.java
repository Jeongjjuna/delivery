package com.sjincho.hun.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(DeliveryApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationHandler(final DeliveryApplicationException e) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(LocalDateTime.now(), e.getHttpStatusMessage(), e.getDetailMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationHandler(final MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();
        Objects.requireNonNull(fieldError);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.getReasonPhrase(), fieldError.getDefaultMessage()));
    }

    /*
     권한 인증중에 발생한 에러는 auth패키지의 CustomAccessDeniedHandle가 핸들링할 수 있도록 던져줍니다.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void throwAccessDeniedException(final AccessDeniedException e) {
        throw e;
    }
}
