package com.oneday.api.controller;

import com.oneday.api.common.jwt.JwtAuthenticationFilter;
import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.model.dto.LoginRequestDto;
import com.oneday.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping(value = "/login")
    public Response<Object> login(
            LoginRequestDto loginDto
    ) {

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }
}
