package com.oneday.api.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ShopReviewRegisterDto {

    private Long shopId;

    private Long ordersId;

    private String content;

    private int score;

    private MultipartFile profileImg1;
    private MultipartFile profileImg2;
    private MultipartFile profileImg3;
}
