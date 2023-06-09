package com.oneday.api.model;

import com.oneday.api.model.base.BaseTime;
import com.oneday.api.model.base.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shopId;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    private String name;
    private int price;

    private int stock;

}
