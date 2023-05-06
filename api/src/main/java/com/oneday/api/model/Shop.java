package com.oneday.api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOP_ID")
    private Long id;

    private String ownerName;

    private String phoneNum;

    private String email;

    @OneToMany(mappedBy = "shop")
    private List<Product> product = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<Orders> orders = new ArrayList<>();

}