package com.oneday.api.model.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String password;

    private String phoneNum;
    private String nickname;
}
