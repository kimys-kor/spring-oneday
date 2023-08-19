package com.oneday.api.common.exception;

import com.oneday.api.common.exception.inteface.CustomException;
import com.oneday.api.common.exception.inteface.ErrorCode;
import com.oneday.api.common.exception.inteface.ErrorResponse;
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
        ErrorCode errorCode = exception.errorCode;
        ErrorResponse response = new ErrorResponse(
                errorCode.name(),
                errorCode.defaultHttpStatus().value(),
                exception.getClass().getName(),
                errorCode.defaultMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, errorCode.defaultHttpStatus());
    }


}

