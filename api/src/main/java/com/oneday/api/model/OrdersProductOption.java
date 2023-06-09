package com.oneday.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;



@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Long ordersProductId;
    private Long productOptionId;

    public OrdersProductOption(Long orderId, Long ordersProductId, Long productOptionId) {
        this.orderId = orderId;
        this.ordersProductId = ordersProductId;
        this.productOptionId = productOptionId;
    }
}
