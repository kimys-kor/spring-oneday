package com.oneday.api.model.dto;

import com.oneday.api.model.base.UserStatus;
import lombok.Data;

@Data
public class RiderReadDto {

    private Long id;

    private String phone;
    private String riderName;
    private String email;

    private double point;

    private UserStatus status;
}
