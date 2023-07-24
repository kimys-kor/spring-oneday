package com.oneday.api.model.dto;

import com.oneday.api.model.base.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserReadDto {

    private UserStatus status;
    private Long id;
    private String phoneNum;
    private String email;
    private String nickname;
    private String createdDt;
    private String lastLogin;
    private int point;
}
