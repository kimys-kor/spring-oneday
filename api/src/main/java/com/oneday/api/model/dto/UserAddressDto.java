package com.oneday.api.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAddressDto {

    private String zonecode;
    // 기본 주소
    private String address;

    // 주소 타입 R 도로명, J 지번
    private String addressType;

    private BigDecimal lat;

    private BigDecimal lon;
}
