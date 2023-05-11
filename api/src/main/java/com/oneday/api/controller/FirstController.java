package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.common.security.PrincipalDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping(value = "/test")
    public Response<Object> Test(
    ) {

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    @GetMapping(value = "/admin/view")
    public Response<Object> Admin(Authentication authentication
                                  ) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principal.getUsername()+"에이피");

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }
}
