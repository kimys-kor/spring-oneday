package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_PRODUCT_ID")
    private Long id;

    private Long ordersId;

    private Long productId;

    private int quantity;

    public Long getId() {
        return this.id;
    }
}
