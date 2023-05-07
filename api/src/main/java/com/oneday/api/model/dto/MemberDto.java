package com.oneday.api.model.dto;

import com.oneday.api.model.MemberRole;
import lombok.Data;

@Data
public class MemberDto {

    private String email;
    private String password;

    private String phoneNum;
    private String nickname;
}
