package com.oneday.api.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrdersDto {

    private Long shopId;

    // 유저 주소id
    private Long userAddressId;

    // 원래금액
    private int price;

    // 할인쿠폰 id
    private Long shopCouponId;

    // 할인금액
    private int discountPrice;
    private List<OrdersProductDto> ordersProduct;

    @Data
    public static class OrdersProductDto {
        private Long productId;

        private int quantity;

        private List<Long> productOptionId;
    }
}
