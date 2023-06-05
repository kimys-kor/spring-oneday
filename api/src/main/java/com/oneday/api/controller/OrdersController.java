package com.oneday.api.controller;

import com.oneday.api.common.UserCheck;
import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.common.security.PrincipalDetails;
import com.oneday.api.model.Member;
import com.oneday.api.model.dto.OrdersSaveDto;
import com.oneday.api.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping(value = "/orders/save")
    public Response<Object> ordersSave(
            OrdersSaveDto ordersSaveDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        Member member = principalDetailis.getUser();


        ordersService.save(member.getId(), ordersSaveDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }
}
