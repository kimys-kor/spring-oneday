package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.common.security.PrincipalDetails;
import com.oneday.api.model.Shop;
import com.oneday.api.model.ShopCoupon;
import com.oneday.api.model.User;
import com.oneday.api.model.base.UserRole;
import com.oneday.api.model.dto.ShopRegisterDto;
import com.oneday.api.service.ShopCouponService;
import com.oneday.api.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final ShopCouponService shopCouponService;


    // 상점 등록 (상점 직접 가입)
    @PostMapping(value = "/shop/save")
    public Response<Map<String, Object>> saveShop(
            ShopRegisterDto shopRegisterDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();

        if(user.getRole()!= UserRole.ROLE_SHOP) return new Response(ResultCode.USER_ROLE_NOT_SHOP);

        Shop savedShop = shopService.save(user.getId(),shopRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, savedShop);
    }

    // 상점 쿠폰 추가
    @PostMapping(value = "/shopcoupon/save")
    public Response<Map<String, Object>> save(
            @RequestParam Long shopId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String startDt,
            @RequestParam String endDt,
            @RequestParam boolean isRatio,
            @RequestParam int amount
    ) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startDt, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endDt, formatter);


        // FIXME shopId를 직접 파라미터로 받는게 아니라 토큰등을 통해서 shopId를 받도록 수정해야함
        ShopCoupon shopCoupon = ShopCoupon.builder()
                .shopId(shopId)
                .title(title)
                .content(content)
                .startDt(startTime)
                .endDt(endTime)
                .isRatio(isRatio)
                .amount(amount)
                .build();
        ShopCoupon save = shopCouponService.save(shopCoupon);
        return new Response( ResultCode.DATA_NORMAL_PROCESSING, save);
    }

    // 상점 쿠폰 수정
    @PostMapping(value = "/shopcoupon/update")
    public Response<Map<String, Object>> update(
            @RequestParam Long shopCouponId,
            @RequestParam Long shopId,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String startDt,
            @RequestParam String endDt,
            @RequestParam boolean isRatio,
            @RequestParam int amount
    ) {
        // 없는 쿠폰 에러 처리
        ShopCoupon shopCoupon = shopCouponService.findById(shopCouponId);
        if(shopCoupon == null) return new Response<>(ResultCode.COUPON_NOT_FOUND);
        // 비율일 경우 할인율 100%넘어갈시 에러 처리
        if(isRatio && amount>100) return new Response<>(ResultCode.COUPON_NOTOVER_100);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(startDt, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endDt, formatter);

        // FIXME 요청을 보낸 유저를 인증을 통해 해당 가게쿠폰만 수정 가능하도록 해야함
        ShopCoupon save = shopCouponService.update(shopCouponId,shopId,title,content,startTime,endTime,isRatio,amount);
        Map<String, Object> result = new HashMap<>();
        result.put("shopCoupon", save);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }

    // 상점 쿠폰 삭제
    @GetMapping(value = "/shopcoupon/delete")
    public Response<Map<String, Object>> delete(
            @RequestParam Long shopCouponId
    ) {
        // FIXME 요청을 보낸 유저를 인증을 통해 해당 가게쿠폰만 삭제 가능하도록 해야함
        shopCouponService.delete(shopCouponId);

        return new Response<>(ResultCode.DATA_NORMAL_PROCESSING);
    }
}
