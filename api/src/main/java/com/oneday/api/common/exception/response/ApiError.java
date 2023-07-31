package com.oneday.api.common.exception.response;

import java.time.LocalDateTime;

public record ApiError(
        String code,
        Integer status,
        String name,
        String message,
        LocalDateTime timestamp
) {
}