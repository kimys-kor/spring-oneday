package com.oneday.api.model.dto;

import com.oneday.api.model.base.OrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrdersReadDto {

    private long ordersId;
    private OrderStatus orderStatus;

    private String ordersNumber;

    private String createdDt;



    private Long shopId;

    private String email;

    private String address;

    private String zipcode;

    private Integer price;
    private Integer shipPrice;
}
