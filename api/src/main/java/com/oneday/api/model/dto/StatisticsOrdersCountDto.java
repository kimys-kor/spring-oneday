package com.oneday.api.model.dto;

import lombok.Data;

@Data
public class StatisticsOrdersCountDto {
    private String shopName;
    private Long count;
}
