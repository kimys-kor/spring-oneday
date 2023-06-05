package com.oneday.api.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopRegisterDto {

    private String name;

    private String ownerName;

    private String phoneNum;

    private String email;

    private BigDecimal lat;
    private BigDecimal lon;

    private String si;
    private String gu;
    private String dong;
    private String restAddress;

}
