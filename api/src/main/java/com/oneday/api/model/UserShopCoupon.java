package com.oneday.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserShopCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long shopCouponId;

    private boolean isUsed;


    public UserShopCoupon(Long userId, Long shopCouponId) {
        this.userId = userId;
        this.shopCouponId = shopCouponId;
        this.isUsed = false;
    }
}
