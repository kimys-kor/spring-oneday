package com.oneday.api.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ShopReadDto {

    private Long id;

    // 가게명
    private String name;
    // 대표자
    private String ownerName;
    private Integer rn;
    private String reviewNumber;

    // 사업자번호
    private String businessNumber;
    // 연락처
    private String contactNumber;
    // 주소
    private String shopAddress;
    // 매장소개
    private String shopDescription;

    // 운영시간
    private String time;

    private String profile1;
    private String profile2;
    private String profile3;

    private BigDecimal lat;
    private BigDecimal lon;
    private BigDecimal distance;

    private Float avgStar;
    private boolean isLike;

    private Integer sales;

    private Long pp;
    private String peoples;

}
