package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long shopId;

    private Long memberId;

    private Long riderId;


    private String address;

    private String zipcode;

    private Integer price;
    private Integer shipPrice;





}
