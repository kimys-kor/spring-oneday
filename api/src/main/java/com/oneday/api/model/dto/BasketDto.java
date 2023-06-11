package com.oneday.api.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasketDto {

    private Long productId;
    private int quantity;


    private List<Long> productOptionId;

}