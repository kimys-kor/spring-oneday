package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.model.Orders;
import com.oneday.api.model.OrdersAssign;
import com.oneday.api.service.OrdersAssignService;
import com.oneday.api.service.OrdersService;
import com.oneday.api.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final OrdersService ordersService;
    private final OrdersAssignService ordersAssignService;


    // 라이더 주문 배정
    @GetMapping(value = "/ordersassign")
    public Response<Object> ordersAssign(
            Long riderId,
            Long ordersId
    ) {
        // 없는 주문 에러 처리
        Orders orders = ordersService.findById(ordersId);
        if(orders== null) return new Response(ResultCode.ORDERS_NOT_FOUND);


        OrdersAssign ordersAssign = OrdersAssign.builder()
                .ordersId(ordersId)
                .riderId(riderId)
                .build();

        ordersAssignService.save(ordersAssign);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 라이더 배달 완료
    @GetMapping(value = "/orderscomplete")
    public Response<Object> orderComplete(
            Long riderId,
            Long ordersId
    ) {
        // 없는 주문 에러 처리
        Orders orders = ordersService.findById(ordersId);
        if(orders== null) return new Response(ResultCode.ORDERS_NOT_FOUND);

        riderService.ordersComplete(ordersId,riderId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }
}
