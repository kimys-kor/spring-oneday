package com.oneday.api.controller;


import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;

import com.oneday.api.model.Member;
import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public Response<Object> login() {
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,"로그인이 완료 되었습니다");
    }

    @GetMapping(value = "/find")
    public Response<Object> find(
//            @RequestParam String email
    ) {
        Optional<Member> byEmail = memberService.findByEmail("wek@naver.com");
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,byEmail);
    }


}
