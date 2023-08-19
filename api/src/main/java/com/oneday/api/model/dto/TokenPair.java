package com.oneday.api.model.dto;

import lombok.Builder;

@Builder
public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
