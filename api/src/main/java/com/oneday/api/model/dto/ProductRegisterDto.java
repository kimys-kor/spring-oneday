package com.oneday.api.model.dto;

import com.oneday.api.model.base.ProductCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ProductRegisterDto {

    private Long shopId;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    private int price;

    private int stock;
}
