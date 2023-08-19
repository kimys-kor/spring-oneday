package com.oneday.api.common.exception.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        Integer status,
        String name,
        String message,
        LocalDateTime timestamp
) {
}