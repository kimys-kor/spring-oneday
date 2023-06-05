package com.oneday.api.model;

import jakarta.persistence.*;

@Entity
public class OrdersProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_PRODUCT_ID")
    private Long id;

    private Long ordersId;

    private Long productId;


}
