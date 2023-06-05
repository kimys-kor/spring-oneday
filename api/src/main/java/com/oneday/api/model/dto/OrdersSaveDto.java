package com.oneday.api.model.dto;

import com.oneday.api.model.OrderStatus;
import lombok.Data;

@Data
public class OrdersSaveDto {

    private OrderStatus status;

    private Long shopId;

    private String address;

    private String zipcode;

    private Integer price;
    private Integer shipPrice;
}
