package com.oneday.api.model.dto;

import com.oneday.api.model.base.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RiderDetailDto {

    private Long id;

    private String phone;
    private String riderName;
    private String email;

    private double point;

    private UserStatus status;

    private Long complete;
    private Integer completePrice;

    private String lastComplete;
}
