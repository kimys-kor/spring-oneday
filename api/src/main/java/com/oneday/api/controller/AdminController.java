package com.oneday.api.controller;

import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.model.*;
import com.oneday.api.model.base.ComplainCategory;
import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.*;
import com.oneday.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;


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
    private final ComplainService complainService;
    private final UserMemoService userMemoService;

    // 대시보드 주문 상태
    @GetMapping(value = "/dashboard/ordersstatus")
    public Response<Object> getDashboardOrderStatus(
    ) {

        Integer waiting = ordersService.countAllByOrderStatusEquals(OrderStatus.WAITING);
        Integer indelivery = ordersService.countAllByOrderStatusEquals(OrderStatus.INDELIVERY);
        Integer complete = ordersService.countAllByOrderStatusEquals(OrderStatus.COMPLETE);
        Integer complain = complainService.countDayComplain();

        Map<String, Object> result = new HashMap<>();
        result.put("waiting", waiting);
        result.put("indelivery", indelivery);
        result.put("complete", complete);
        result.put("complain", complain);


        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }

    // 대시보드 일주일 주문량 라인 차트
    @GetMapping(value = "/dashboard/weekorders")
    public Response<Object> getWeekOrdersData(
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
    public Response<Object> getFivedayAmount(
    ) {

        List result = ordersService.countFiveDayAmount();
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }

    // 대시보드 최근 일주일 불편 신고 파이 차트
    @GetMapping(value = "/dashboard/complain")
    public Response<Object> getWeekComplain(
    ) {

        Integer broken = complainService.countWeekComplain(ComplainCategory.BROKEN);
        Integer delay = complainService.countWeekComplain(ComplainCategory.DELAY);
        Integer missing = complainService.countWeekComplain(ComplainCategory.MISSING);
        Integer paymentFail = complainService.countWeekComplain(ComplainCategory.PAYMENTFAILED);

        Map<String, Object> map = new HashMap<>();
        map.put("broken", broken);
        map.put("delay", delay);
        map.put("missing", missing);
        map.put("paymentFail", paymentFail);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING,map);
    }

    // 대시보드 일주일 신규 회원 라인 차트
    @GetMapping(value = "/dashboard/weekuser")
    public Response<Object> getWeekNewUserData(
    ) {

        List data = userService.countAllByCreatedDtBetween();

        List result = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "week");
        map.put("data", data);
        result.add(map);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }

    // 유저 리스트
    @GetMapping(value = "/user/findall")
    public Response<Object> findAllUser(Pageable pageable
    ) {
        Map<String, Object> all = userService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, all);
    }

    // 유저 상세
    @GetMapping(value = "/user/findone")
    public Response<Object> findOneUser(
            @RequestParam Long userId
    ) {
        Map<String, Object> userInfo = userService.findById(userId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,userInfo);
    }

    // 유저주문 상품 통계
    @GetMapping(value = "/user/findone/userstatistics")
    public Response<Object> findOneUserStatistics(
            @RequestParam Long userId
    ) {
        Map<String, Object> result = ordersService.findOneUserStatistics(userId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result);
    }

    // 유저통합 주문 조회
    @GetMapping(value = "/user/findone/orderhistory")
    public Response<Object> findOneUserOrderHistory(
            @RequestParam Long userId,
            @RequestParam(required = false) String startDt,
            @RequestParam(required = false) String endDt,
            @RequestParam(required = false, defaultValue = "all") String orderStatus,
            Pageable pageable
    ) {
        OrderStatus status = OrderStatus.of(orderStatus);
        Map<String, Object> result = ordersService.findUserOrdersHistory(userId, startDt, endDt, status, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result );
    }

    // 유저 적립금 추가
    @GetMapping(value = "/user/add/point")
    public Response<Object> updateUserPoint(@RequestParam Long userId,
                                            @RequestParam Integer point) {
        userService.addPoint(userId, point);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 유저 임시 비밀번호 생성
    @GetMapping(value = "/user/password/reset")
    public Response<Object> userPasswordReset(@RequestParam Long userId) {
        String tempPassword = userService.updatePassword(userId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,tempPassword);
    }

    // 유저 정보 업데이트
    @PostMapping(value = "/user/update/info")
    public Response<Object> updateUserInfo(@RequestParam Long userId,
                                           @RequestParam String userNickname,
                                           @RequestParam String userGrade) {
        userService.updateInfo(userId, userNickname, userGrade);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 유저 메모 저장
    @PostMapping(value = "/user/save/memo")
    public Response<Object> saveUserMemo(@RequestParam Long userId,
                                           @RequestParam String content) {

        userMemoService.save(userId, content);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 상점 리스트
    @GetMapping(value = "/shop/findall")
    public Response<Object> findAllShop(
            @RequestParam(required = false,defaultValue = "") String orderCondition,
            @RequestParam(required = false,defaultValue = "") String address,
            @RequestParam(required = false,defaultValue = "") String keyword,
            Pageable pageable
    ) {
        Map<String, Object> all = shopService.findShopListForAdmin(orderCondition, address, keyword, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, all);
    }

    // 상점 상세 페이지
    @GetMapping(value = "/shop/detail")
    public Response<Object> getShopDetail(
            @RequestParam Long shopId
    ) {
        Map<String, Object> result = shopService.getShopDetail(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result);
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

    // 상점 상세 페이지 통합주문내역
    @GetMapping(value = "/shop/orders/statistics")
    public Response<Object> getShopOrderStatistics(
            @RequestParam Long shopId
    ) {
        Map<String, Object> result = ordersService.findShopStatistics(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result);
    }

    // 상점통합 주문 조회
    @GetMapping(value = "/shop/orders/list")
    public Response<Object> findShopOrderHistory(
            @RequestParam Long shopId,
            @RequestParam(required = false) String startDt,
            @RequestParam(required = false) String endDt,
            @RequestParam(required = false, defaultValue = "all") String orderStatus,
            Pageable pageable
    ) {
        OrderStatus status = OrderStatus.of(orderStatus);
        Map<String, Object> result = ordersService.findShopOrdersHistory(shopId, startDt, endDt, status, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result );
    }


    // 상점 등록 (어드민 가입)
    @PostMapping(value = "/shop/save/byadmin")
    public Response<Object> saveShopByAdmin(
            long userId,
            ShopRegisterDto shopRegisterDto
    ) {
        Shop savedShop = shopService.save(userId,shopRegisterDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, savedShop);
    }

    // 상점페이지 상품관리
    @GetMapping(value = "/shop/product/list")
    public Response<Object> findShopProduct(
            @RequestParam Long shopId,
            Pageable pageable
    ) {
        List<Product> result = productService.findAllByShopIdEqualsForAdmin(shopId, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result );
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

    // 라이더 리스트
    @GetMapping(value = "/rider/findall")
    public Response<Object> findAllRider(Pageable pageable
    ) {
        Map<String, Object> result = riderService.findAll(pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result);
    }

    // 라이더 상세 페이지
    @GetMapping(value = "/rider/findone")
    public Response<Object> findRiderDetail(
            Long riderId
    ) {
        Map<String, Object> rider = riderService.findByIdForAdmin(riderId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  rider);
    }

    // 라이더 상세 페이지 통합주문내역
    @GetMapping(value = "/rider/orders/statistics")
    public Response<Object> getRiderOrderStatistics(
            @RequestParam Long riderId
    ) {
        Map<String, Object> result = ordersService.findRiderStatistics(riderId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result);
    }


    // 라이더 통합 주문 조회
    @GetMapping(value = "/rider/orders/list")
    public Response<Object> findRiderOrders(
            @RequestParam Long riderId,
            @RequestParam(required = false) String startDt,
            @RequestParam(required = false) String endDt,
            @RequestParam(required = false, defaultValue = "all") String orderStatus,
            Pageable pageable
    ) {
        OrderStatus status = OrderStatus.of(orderStatus);
        Map<String, Object> rider = ordersService.findRiderOrdersHistory(riderId,startDt, endDt, status, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  rider);
    }

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


    // 라이더 삭제
    @GetMapping(value = "/rider/delete")
    public Response<Object> deleteRider(
            Long riderId
    ) {
        riderService.delete(riderId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
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
        System.out.println(startDt+"llll");
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
        Orders updatedOrders = ordersService.updateOrders(ordersId, OrderStatus.CANCEL);
        Map<String, Object> result = new HashMap<>();
        result.put("orders", orders);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING, updatedOrders);
    }

    // 주문관리 페이지
    @GetMapping(value = "/orders/list")
    public Response<Map<String, Object>> ordersList(
            @RequestParam(required = false, defaultValue = "")String startDt,
            @RequestParam(required = false, defaultValue = "")String endDt,
            Pageable pageable) {

        Page<OrdersReadDto> all = ordersService.findAll(startDt, endDt, OrderStatus.All, pageable);

        Map<String, Object> result = new HashMap<>();
        result.put("totalItem", all.getTotalElements());
        result.put("orders", all.getContent());

        return new Response(ResultCode.DATA_NORMAL_PROCESSING, result);
    }
    
    // 정산 페이지 주문건수 통계
    @GetMapping(value = "/statistics/orders/count")
    public Response<Map<String, Object>> statisticsOrdersCount(
            @RequestParam(required = false, defaultValue = "")String startDt,
            @RequestParam(required = false, defaultValue = "")String endDt,
            @RequestParam(required = false, defaultValue = "all")String ordersStats,
            Pageable pageable) {

        OrderStatus status = OrderStatus.of(ordersStats);
        Map<String, Object> chartData = ordersService.statisticsOrdersCount(startDt, endDt, status, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, chartData);
    }

    @GetMapping(value = "/statistics/orders/sum")
    public Response<Map<String, Object>> statisticsOrdersSum(
            @RequestParam(required = false, defaultValue = "")String startDt,
            @RequestParam(required = false, defaultValue = "")String endDt,
            @RequestParam(required = false, defaultValue = "all")String ordersStats,
            Pageable pageable) {

        OrderStatus status = OrderStatus.of(ordersStats);
        Map<String, Object> chartData = ordersService.statisticsOrdersSum(startDt, endDt, status, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, chartData);
    }






}
