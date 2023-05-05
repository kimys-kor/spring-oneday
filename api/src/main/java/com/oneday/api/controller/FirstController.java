package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping(value = "/test")
    public Response<Object> Test(
    ) {

        return new Response<>("hi",ResultCode.DATA_NORMAL_PROCESSING);
    }
}
