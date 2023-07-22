package com.oneday.api.model.dto;

import com.oneday.api.model.base.OrderStatus;
import lombok.Data;

@Data
public class OrdersReadDto {

    private long ordersId;
    private OrderStatus orderStatus;

    private String ordersNumber;



    private Long shopId;

    private Long userId;

    private String address;

    private String zipcode;

    private Integer price;
    private Integer shipPrice;
}
