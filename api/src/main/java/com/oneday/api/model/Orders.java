package com.oneday.api.model;

import com.oneday.api.common.utils.OrderNumberGenerator;
import com.oneday.api.model.base.BaseTime;
import com.oneday.api.model.base.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Orders extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String ordersNumber = OrderNumberGenerator.generateOrderNumber();

    private Long shopId;
    private Long userId;

    private Long userAddresId;

    private int price;
    // 배송비
    private int shipPrice;
    // 할인금액
    private int discountPrice;

    private Long shopCouponId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Orders(Long shopId, Long userId, Long userAddresId, int price, int shipPrice, int discountPrice, Long shopCouponId, OrderStatus orderStatus) {
        this.shopId = shopId;
        this.userId = userId;
        this.userAddresId = userAddresId;
        this.price = price;
        this.shipPrice = shipPrice;
        this.discountPrice = discountPrice;
        this.shopCouponId = shopCouponId;
        this.orderStatus = orderStatus;
    }
}
