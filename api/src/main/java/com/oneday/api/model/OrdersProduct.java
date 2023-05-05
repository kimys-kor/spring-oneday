package com.oneday.api.model;

import jakarta.persistence.*;

@Entity
public class OrdersProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_PRODUCT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDERS_ID")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
