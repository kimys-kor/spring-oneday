package com.oneday.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record SignUpDto() {

    @Builder
    public record SignUpRequestDto(
            String username,
            @JsonProperty("password")   // jackson (spring)
            String ramPassword
    ){}

    @Builder
    public record SignUpResponseDto(
            String username,
            @JsonProperty("password")
            String ramPassword
    ){}



}
