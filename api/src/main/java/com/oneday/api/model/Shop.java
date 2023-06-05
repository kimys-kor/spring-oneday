package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Shop extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOP_ID")
    private Long id;

    private String name;

    private String ownerName;

    private String phoneNum;

    private String email;

    private BigDecimal lat;

    private  BigDecimal lon;

    private String si;
    private String gu;
    private String dong;



}
