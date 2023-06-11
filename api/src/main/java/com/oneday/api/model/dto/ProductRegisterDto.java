package com.oneday.api.model.dto;

import com.oneday.api.model.base.ProductCategory;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ProductRegisterDto {

    @Nullable
    private Long shopId;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    private String name;
    private int price;
    private int stock;
}
