package com.oneday.api.controller;


import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;

import com.oneday.api.model.Member;
import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping(value = "/login")
    public Response<Object> login(@AuthenticationPrincipal UserDetails userDetails) {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,userDetails.getUsername());
    }

    @GetMapping(value = "/find")
    public Response<Object> find(
//            @RequestParam String email
    ) {
        Optional<Member> byEmail = memberService.findByEmail("wek@naver.com");
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,byEmail);
    }


}
