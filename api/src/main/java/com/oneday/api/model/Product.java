package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    private Long shopId;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;


    private int price;

    private int stock;

}
