package com.oneday.api.common.exception;

import com.oneday.api.common.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handlerCustomException(CustomException exception) {
        // TODO use builder
        ErrorCode errorCode = exception.CODE;
        ErrorResponse response = new ErrorResponse(
                errorCode.name(),
                errorCode.status.value(),
                exception.getClass().getName(),
                errorCode.message,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, errorCode.status);
    }


}

