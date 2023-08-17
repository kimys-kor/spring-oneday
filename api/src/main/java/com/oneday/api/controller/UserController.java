package com.oneday.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oneday.api.common.exception.CustomException;
import com.oneday.api.common.jwt.JwtProperties;
import com.oneday.api.common.response.Response;
import com.oneday.api.common.response.ResultCode;
import com.oneday.api.common.security.PrincipalDetails;
import com.oneday.api.model.*;
import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.*;
import com.oneday.api.service.*;
import io.micrometer.observation.Observation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ShopService shopService;
    private final ShopLikeService shopLikeService;
    private final ShopCouponService shopCouponService;
    private final ShopReviewService shopReviewService;
    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final OrdersService ordersService;
    private final OrdersCancleService ordersCancleService;
    private final UserBasketProductService userBasketProductService;
    private final UserShopCouponService userShopCouponService;

    @GetMapping(value = "/token-check")
    public Response<Object> userCheck(
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();

        if(user == null) return new Response<>( ResultCode.AUTH_PERMISSION_DENY);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    @PostMapping(value = "/login")
    public Response<Object> login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        PrincipalDetails principalDetailis = (PrincipalDetails) authenticate.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetailis.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetailis.getUser().getId())
                .withClaim("username", principalDetailis.getUser().getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    @PostMapping(value = "/join")
    public Response<Object> join(
            UserDto userDto
    ) throws CustomException {
        userService.join(userDto);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 이메일, 비밀번호 찾기 (문자인증)
    // 내정보 수정
    // 내정보 확인

    //  상점 리스트
    @GetMapping(value = "/shop/findall")
    public Response<Object> findAllShop(
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lon,
            @RequestParam(required = false)String orderCondition,
            @RequestParam(required = false)Integer distance,
            @RequestParam(required = false)String keyword,
            Pageable pageable
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();

        Page<ShopReadDto> shopList = shopService.findShopList(user.getId(), lat,lon, orderCondition, distance, keyword, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, shopList);
    }

    // 상점별 상품 리스트
    @GetMapping(value = "/product/findall")
    public Response<Object> findAllProduct(
            Long shopId,
            Pageable pageable
    ) {
        List<Product> allByShopIdEquals = productService.findAllByShopIdEqualsForAdmin(shopId, pageable);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING,  allByShopIdEquals);
    }

    // 상품 옵션 리스트
    @GetMapping(value = "/productoption/findall")
    public Response<Map<String, Object>> findAllProductOption(
            @RequestParam Long productId
    ) {
        // 없는 메뉴 에러 처리
        if(productService.findById(productId) == null)  return new Response<>( ResultCode.PRODUCT_NOT_FOUND);

        List<Map<String, Object>> productOptions = productOptionService.findByProductIdEquals(productId);
        Observation.CheckedCallable<String, Throwable> stringThrowableCheckedCallable = () -> "hello world!";
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, productOptions);
    }

    // 주문 하기
    @PostMapping(value = "/orders/save")
    public Response<Object> ordersSave(
            @RequestBody OrdersDto ordersDto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();

        // 없는 상점일시 에러 처리
        Shop shop = shopService.findById(ordersDto.getShopId());
        if(shop== null) return new Response(ResultCode.SHOP_NOT_FOUND);

        // 없는 상품 일시 에러 처리 && 해당 가게의 상품 아닐시 에러 처리 && 해당 상품의 옵션 아닐시 에러 처리
        List<OrdersDto.OrdersProductDto> ordersProduct = ordersDto.getOrdersProduct();
        for (OrdersDto.OrdersProductDto ordersProductDto : ordersProduct) {
            Long productId = ordersProductDto.getProductId();
            Product product = productService.findById(productId);
            if(product== null) return new Response(ResultCode.PRODUCT_NOT_FOUND);
            if(product.getShopId() != ordersDto.getShopId()) return new Response(ResultCode.PRODUCT_NOT_CORRECT_SHOP);
            List<Long> productOption = productOptionService.findIdListByProductIdEquals(product.getId());
            for (Long optionId:ordersProductDto.getProductOptionId()) {
                if(!productOption.contains(optionId)) return new Response(ResultCode.PRODUCTOPTION_NOT_CORRECT_PRODUCT);
            }
        }

        Orders orders = ordersService.save(user.getId(), ordersDto);
        // 가격 안맞을시 에러 처리
        if(orders == null) return new Response<>( ResultCode.ORDERS_PRICE_NOTCORRECT);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING,orders);
    }

    // 회원 주문 취소 신청
    @GetMapping(value="/orders/cancle/request")
    public Response<Map<String, Object>> ordersCancleRequest(
            @RequestParam Long ordersId ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();

        Orders orders = ordersService.findById(ordersId);
        // 없는 주문 에러 처리
        if(orders== null) return new Response(ResultCode.ORDERS_NOT_FOUND);
        // 접수된 주문 에러 처리
        if(orders.getOrderStatus() != OrderStatus.WAITING) return new Response(ResultCode.ORDERS_CANNOT_CANCLE);
        
        // 주문한 유저 아닐시 에러 처리
        Orders byId = ordersService.findById(ordersId);
        if(byId.getUserId() != user.getId()) return new Response<>( ResultCode.AUTH_PERMISSION_DENY);

        ordersCancleService.save(user.getId(),ordersId);

        return new Response<>( ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 회원 주문내역 확인
    @GetMapping(value = "/orders/findall")
    public Response<Map<String, Object>> findAllOrders(
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();

        List<Map<String, Object>> byUserId = ordersService.findByUserId(user.getId());
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, byUserId);
    }

    // 가게 좋아요
    @GetMapping(value = "/shop/like")
    public Response<Map<String, Object>> likeShop(
            @RequestParam Long shopId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();

        shopLikeService.save(shopId, user.getId());

        return new Response<>( ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 가게별 쿠폰 목록
    @GetMapping(value = "/shopcoupon/findall")
    public Response<Map<String, Object>> findAllShopCoupon(
            @RequestParam Long shopId
    ) {
        List<ShopCoupon> allByShopIdEquals = shopCouponService.findAllByShopIdEquals(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, allByShopIdEquals);
    }

    // 가게 리뷰 등록
    @PostMapping(value = "/shopreview/save")
    public Response<Map<String, Object>> saveShopReview(
            ShopReviewRegisterDto dto
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();
        Long userId = user.getId();

        // 없는 상점일시 에러
        Shop shop = shopService.findById(dto.getShopId());
        if(shop == null) return new Response(ResultCode.SHOP_NOT_FOUND);

        Orders orders = ordersService.findById(dto.getOrdersId());
        // 없는 주문 에러 처리
        if(orders == null) return new Response(ResultCode.ORDERS_NOT_FOUND);
        // 주문한 회원 아닐시 에러 처리
        if(orders.getUserId() != userId) return new Response(ResultCode.REVIEW_ONLY_ORDER_USER);



        shopService.updatePluReview(dto.getShopId());
        ShopReview review = shopReviewService.save(userId, dto);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING,review);
    }

    // 가게별 리뷰 리스트
    @GetMapping(value = "/shopreview/findall")
    public Response<Map<String, Object>> findAllShopReview(
            Long shopId
    ) {

        List<ShopReview> shopReviewList = shopReviewService.findAllByShopIdEquals(shopId);
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, shopReviewList);
    }

    // 리뷰 삭제
    @GetMapping(value = "/shopreview/delete")
    public Response<Map<String, Object>> deleteShopReview(
            Long shopReviewId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();
        Long userId = user.getId();

        ShopReview review = shopReviewService.findById(shopReviewId);
        // 없는 리뷰 일시 에러처리
        if(review == null) return new Response(ResultCode.REVIEW_NOT_FOUND);
        // 리뷰작성자 아닐시 에러 처리
        if(review.getUserId() != userId) return new Response(ResultCode.REVIEW_NOT_CORRECT_WRITER);

        shopReviewService.delete(review);
        shopService.updateMinusReview(review.getShopId());
        return new Response(ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 장바구니 저장
    @PostMapping(value = "/basket/save")
    public Response<Map<String, Object>> saveBasket(
            @RequestBody BasketDto basketDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();
        Long userId = user.getId();

        Product product = productService.findById(basketDto.getProductId());
        List<Map<String, Object>> basketList = userBasketProductService.findAllByUserIdEquals(userId);

        // 1개 이상 담을시 에러 처리
        if(basketList.size() >= 10) return new Response<>( ResultCode.BASKET_ONLY_ONEITEM);
        // 다른 가게 상품 담을시 에러 처리
        for (Map<String, Object> basket : basketList) {
            int shop_id = (int)basket.get("shop_id");
            if (shop_id != product.getShopId()) return new Response<>( ResultCode.BASKET_ONLY_ONESHOP);
        }

        UserBasketProduct basketProduct = userBasketProductService.save(basketDto, userId, product.getShopId());
        return new Response(ResultCode.DATA_NORMAL_PROCESSING, basketProduct);
    }

    // 장바구니 목록
    @GetMapping(value = "/basket/getall")
    public Response<Map<String, Object>> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();
        Long userId = user.getId();

        List<Map<String, Object>> allByUserIdEquals = userBasketProductService.findAllByUserIdEquals(userId);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING, allByUserIdEquals);
    }

    // 장바구니 아이템 비우기
    @GetMapping(value = "/basket/delete")
    public Response<Map<String, Object>> get(
            @RequestParam Long userBasketProductId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();
        Long userId = user.getId();

        String result = userBasketProductService.deleteByUserBasketProductId(userId, userBasketProductId);

        // 장바구니 유저 아닐시 삭제 불가
        if(result.equals("권한없음")) return new Response<>( ResultCode.AUTH_PERMISSION_DENY);
        return new Response<>( ResultCode.DATA_NORMAL_PROCESSING);
    }
    
    // 쿠폰 발급 받기
    @GetMapping(value = "/shopcoupon/save")
    public Response<Map<String, Object>> saveShopCoupon(
            @RequestParam Long shopCouponId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();
        Long userId = user.getId();

        // 쿠폰 없을시 에러 처리
        ShopCoupon shopCoupon = shopCouponService.findById(shopCouponId);
        if(shopCoupon == null) return new Response<>( ResultCode.SHOPCOUPON_NOT_FOUND);

        UserShopCoupon save = userShopCouponService.save(userId, shopCouponId);

        // 이미 발급했을경우 에러처리
        if(save == null) return new Response<>( ResultCode.EVENT_DUPLICATE);

        return new Response<>( ResultCode.DATA_NORMAL_PROCESSING);
    }

    // 발급한 쿠폰 리스트 (마이페이지 용도)
    @GetMapping(value = "/shopcoupon/mycoupon")
    public Response<Map<String, Object>> myCouponList(
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();
        Long userId = user.getId();

        List<Map<String,Object>> myCoupons = userShopCouponService.findAllByUserIdEquals(userId);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING, myCoupons);
    }

    // 쿠폰 사용 가능 여부
    // FIXME 발급한 쿠폰 리스트 (사용 가능 여부 포함 해야함)
    @GetMapping(value = "/shopcoupon/usablecheck")
    public Response<Map<String, Object>> isUsable(
            @RequestParam Long shopId,
            @RequestParam Long shopCouponId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetailis.getUser();
        Long userId = user.getId();

        ShopCoupon byId = shopCouponService.findById(shopCouponId);
        // 없는 쿠폰 에러 처리
        if(byId == null) return new Response<>( ResultCode.COUPON_NOT_FOUND);
        // 쿠폰 유효기간 아닐시 에러 처리
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(byId.getStartDt()) || now.isAfter(byId.getEndDt())) return new Response<>( ResultCode.COUPON_NOT_OPEN);
        // 해당 상점 쿠폰 아닐시 에러 처리
        if (byId.getShopId() != shopId) return new Response<>( ResultCode.COUPON_NOT_USABLE);

        Map<String, Object> result = new HashMap<>();
        result.put("isUsable", true);

        return new Response(ResultCode.DATA_NORMAL_PROCESSING,result);
    }
}
