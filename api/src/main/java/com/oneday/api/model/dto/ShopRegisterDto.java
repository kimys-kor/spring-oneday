package com.oneday.api.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ShopRegisterDto {

    // 가게명
    private String name;
    // 대표자
    private String ownerName;
    // 사업자번호
    private String businessNumber;
    // 연락처
    private String contactNumber;
    // 주소
    private String shopAddress;
    // 운영시간
    private String time;
    // 가게소개
    private String shopDescription;
    // 위도
    private BigDecimal lat;
    //경도
    private BigDecimal lon;

    private MultipartFile profileImg1;
    private MultipartFile profileImg2;
    private MultipartFile profileImg3;

}
