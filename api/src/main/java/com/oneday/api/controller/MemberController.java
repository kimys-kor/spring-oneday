package com.oneday.api.controller;


import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;

import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final UserService userService;

    @PostMapping(value = "/join")
    public Response<Object> join(
            MemberDto memberDto
    ) {
        userService.join(memberDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }




}
