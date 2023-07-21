package com.oneday.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneday.api.model.base.UserGrade;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDetailDto {
    private Long userId;
    private String nickname;;
    private UserGrade userGrade;
    private int point;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;
    private String phoneNum;

}
