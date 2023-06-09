package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.common.security.PrincipalDetails;
import com.oneday.api.model.User;
import com.oneday.api.model.dto.OrdersDto;
import com.oneday.api.model.dto.OrdersSaveDto;
import com.oneday.api.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping(value = "/orders/save")
    public Response<Object> ordersSave(
            OrdersDto ordersDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();


        ordersService.save(user.getId(), ordersDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }
}
