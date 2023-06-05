package com.oneday.api.controller;


import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;

import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping(value = "/join")
    public Response<Object> join(
            MemberDto memberDto
    ) {
        memberService.join(memberDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }




}
