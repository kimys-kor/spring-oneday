package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.model.*;
import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.*;
import com.oneday.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ShopService shopService;
    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final RiderService riderService;
    private final OrdersService ordersService;

    // 대시보드 주문 상태
    @GetMapping(value = "/dashboard/ordersstatus")
    public Response<Object> dashboardOrderStatus(
    ) {

        Integer waiting = ordersService.countAllByOrderStatusEquals(OrderStatus.WAITING);
        Integer indelivery = ordersService.countAllByOrderStatusEquals(OrderStatus.INDELIVERY);
        Integer complete = ordersService.countAllByOrderStatusEquals(OrderStatus.COMPLETE);

        Map<String, Object> result = new HashMap<>();
        result.put("waiting", waiting);
        result.put("indelivery", indelivery);
        result.put("complete", complete);


        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }

    // 대시보드 라인 차트
    @GetMapping(value = "/dashboard/weekorders")
    public Response<Object> weekOrdersData(
    ) {

        List data = ordersService.countAllByCreatedDtBetween();

        List result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "week");
        map.put("data", data);
        result.add(map);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }

    // 대시보드 5일 주문 금액 막대 차트
    @GetMapping(value = "/dashboard/fiveday/amount")
    public Response<Object> fivedayAmount(
    ) {

        List result = ordersService.countFiveDayAmount();
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }


    // 유저 상세
    @GetMapping(value = "/user/findone")
    public Response<Object> findOneUser(
            @RequestParam Long userId
    ) {
        User byId = userService.findById(userId).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,byId);
    }

    // 유저 리스트
    @GetMapping(value = "/user/findall")
    public Response<Object> findAllUser(Pageable pageable
    ) {
        Page<UserDto> all = userService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,all);
    }

    // 상점 등록 (어드민 가입)
    @PostMapping(value = "/shop/save/byadmin")
    public Response<Map<String, Object>> saveShopByAdmin(
            long userId,
            ShopRegisterDto shopRegisterDto
    ) {

        Shop savedShop = shopService.save(userId,shopRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, savedShop);
    }

    // 상점 수정
    @PostMapping(value = "/shop/update")
    public Response<Object> updateShop(
            Long shopId,
            ShopRegisterDto shopRegisterDto
    ) {
        shopService.update(shopId,shopRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 상점 상세
    @GetMapping(value = "/shop/findone")
    public Response<Object> findOneShop(
            @RequestParam Long shopId
    ) {
        Shop byId = shopService.findById(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, byId);
    }

    // 상점 삭제 (상품 모두 삭제, 상품의 옵션들 모두삭제)
    @GetMapping(value = "/shop/delete")
    public Response<Object> deleteShop(
            @RequestParam Long shopId
    ) {
        // 없는 상점일시 에러
        Shop byId = shopService.findById(shopId);
        if(byId == null) return new Response(ResultCode.SHOP_NOT_FOUND);

        shopService.delete(byId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, byId);
    }

    // 상품 추가
    @PostMapping(value = "/product/save")
    public Response<Object> saveProduct(
            ProductRegisterDto productRegisterDto
    ) {
        // 없는 상점일시 에러 처리
        Shop shop = shopService.findById(productRegisterDto.getShopId());
        if(shop== null) return new Response(ResultCode.SHOP_NOT_FOUND);

        Product save = productService.save(productRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  save);
    }

    // 상품 업데이트
    @PostMapping(value = "/product/update")
    public Response<Object> updateProduct(
            Long productId,
            ProductRegisterDto productRegisterDto
    ) {
        // 없는 상품일시 에러 처리
        Product product = productService.findById(productId);
        if(product== null) return new Response(ResultCode.PRODUCT_NOT_FOUND);

        Product update = productService.update(productId, productRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  update);
    }

    // 상품 삭제 (옵션들 모두 삭제)
    @GetMapping(value = "/product/delete")
    public Response<Object> deleteProduct(
            Long productId
    ) {
        // 없는 상품일시 에러 처리
        Product product = productService.findById(productId);
        if(product== null) return new Response(ResultCode.PRODUCT_NOT_FOUND);

        productService.deleteByIdEquals(productId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 상품 옵션 추가
    @PostMapping(value = "/productoption/save")
    public Response<Map<String, Object>> saveProductOption(
            @RequestParam Long productId,
            @RequestParam String name,
            @RequestParam int price
    ) {
        // 없는 메뉴 에러 처리
        if(productService.findById(productId) == null)  return new Response<>( ResultCode.PRODUCT_NOT_FOUND);

        productOptionService.save(productId,name,price);

        return new Response<>( ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 상품 옵션 수정
    @PostMapping(value = "/productoption/update")
    public Response<Map<String, Object>> updateProductOption(
            @RequestParam Long productOptionId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) int price
    ) {
        // 없는 메뉴 에러 처리
        if(productOptionService.findById(productOptionId) == null)  return new Response<>( ResultCode.PRODUCTOPTION_NOT_FOUND);

        productOptionService.update(productOptionId,name,price);

        return new Response<>( ResultCode.DATA_NORMAL_PROCESSING);
    }
    
    // 상품 옵션 삭제
    @GetMapping(value = "/productoption/delete")
    public Response<Map<String, Object>> deleteProductOption(
            @RequestParam Long productOptionId
    ) {

        // 없는 메뉴 에러 처리
        if(productOptionService.findById(productOptionId) == null)  return new Response<>( ResultCode.PRODUCTOPTION_NOT_FOUND);

        productOptionService.delete(productOptionId);

        return new Response<>( ResultCode.DATA_NORMAL_PROCESSING);
    }


    // 주문 현황 리스트
    @GetMapping(value = "/orders/findall")
    public Response<Object> findAllOrders(
            @RequestParam(required = false) String startDt,
            @RequestParam(required = false) String endDt,
            @RequestParam(required = false) OrderStatus orderStatus,
            Pageable pageable
    ) {
        Page<OrdersReadDto> all = ordersService.findAll(startDt,endDt,orderStatus,pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  all);
    }

    // 가게별 주문내역 확인
    @GetMapping(value = "/orders/shop/findall")
    public Response<Map<String, Object>> findShopOrders(
            @RequestParam Long shopId
    ) {

        List<Map<String, Object>> byShop = ordersService.findShopOrders(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,byShop);
    }

    // 가게 주문 취소 신청 승인
    @GetMapping(value = "/orders/cancle/accept")
    public Response<Map<String, Object>> orderCancleAccept(
            @RequestParam Long ordersId ) {

        Orders orders = ordersService.findById(ordersId);
        // 없는 주문 에러 처리
        if(orders== null) return new Response(ResultCode.ORDERS_NOT_FOUND);
        // 접수된 주문 에러 처리
        if(orders.getOrderStatus() != OrderStatus.WAITING) return new Response(ResultCode.ORDERS_CANNOT_CANCLE);

        // 주문 취소 변경
        Orders updatedOrders = ordersService.updateOrders(ordersId, OrderStatus.CANCLE);
        Map<String, Object> result = new HashMap<>();
        result.put("orders", orders);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING, updatedOrders);
    }

    // 가게

    // 라이더 등록
    @PostMapping(value = "/riders/save")
    public Response<Object> ridersSave(
            RiderRegisterDto dto
    ) {
        Rider save = riderService.save(dto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  save);
    }

    // 라이더 수정
    @PostMapping(value = "/riders/update")
    public Response<Object> ridersSave(
            Long riderId,
            RiderRegisterDto dto
    ) {
        Rider updateRider = riderService.update(riderId,dto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  updateRider);
    }

    // 라이더 리스트
    @GetMapping(value = "/rider/findall")
    public Response<Object> findAllRider(
            Pageable pageable
    ) {
        Page<RiderReadDto> all = riderService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  all);
    }

    // 라이더 상세정보
    @GetMapping(value = "/rider/findone")
    public Response<Object> findAllRider(
            Long riderId
    ) {
        Rider byId = riderService.findById(riderId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  byId);
    }

    // 라이더 삭제
    @GetMapping(value = "/rider/delete")
    public Response<Object> deleteRider(
            Long riderId
    ) {
        riderService.delete(riderId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }





}
