package com.oneday.api.common.exception.inteface;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String code,
        Integer status,
        String name,
        String message,
        LocalDateTime timestamp
) {
}