package com.oneday.api.model.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Data
public class OrdersDto {

    private Long shopId;

    // 유저 주소id
    private Long userAddressId;

    // 원래금액
    private int price;

    // 할인쿠폰 id
    @Nullable
    private Long shopCouponId;

    // 할인금액
    private int discountPrice;

    private List<OrdersProductDto> ordersProduct;

    public Long getShopCouponId() {
        return shopCouponId != null ? shopCouponId : 0L; // Assign default value when shopCouponId is null
    }

    @Data
    public static class OrdersProductDto {
        private Long productId;

        private int quantity;

        private List<Long> productOptionId;
    }
}
