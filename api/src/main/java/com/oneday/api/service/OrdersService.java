package com.oneday.api.service;

import com.oneday.api.model.*;
import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.OrdersDto;
import com.oneday.api.model.dto.OrdersReadDto;
import com.oneday.api.repository.OrdersCustomRepository;
import com.oneday.api.repository.OrdersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersService {

    @PersistenceContext
    EntityManager em;
    private final OrdersRepository ordersRepository;
    private final OrdersProductService ordersProductService;
    private final OrdersProductOptionService ordersProductOptionService;
    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final OrdersCustomRepository ordersCustomRepository;
    private final ShopCouponService shopCouponService;
    private final UserShopCouponService userShopCouponService;


    public Orders saveOrders(Orders orders) {
        return ordersRepository.save(orders);
    }


    public Orders save(Long userId, OrdersDto dto) {
        // 가격 안맞을시 에러 처리
        boolean calResult = calculatePrice(dto);
        if (!calResult) return null;

        Orders orders = Orders.builder()
                .shopId(dto.getShopId())
                .userId(userId)
                .userAddresId(dto.getUserAddressId())
                .price(dto.getPrice())
                .shopCouponId(dto.getShopCouponId())
                .discountPrice(dto.getDiscountPrice())
                .orderStatus(OrderStatus.WAITING)
                .build();
        Orders save = ordersRepository.save(orders);

        // 주문에 속한 여러 상품들 저장
        List<OrdersDto.OrdersProductDto> ordersProduct = dto.getOrdersProduct();
        for (OrdersDto.OrdersProductDto ordersProductDto : ordersProduct) {
            OrdersProduct save1 = ordersProductService.save(save.getId(), ordersProductDto.getProductId(), ordersProductDto.getQuantity());

            // 주문에 속한 상품에 속한 여러 옵션들 저장
            List<Long> productOptionIdList = ordersProductDto.getProductOptionId();
            for (Long productOptionId : productOptionIdList) {
                ordersProductOptionService.save(save.getId(),save1.getId(), productOptionId);
            }
        }
        // 쿠폰 사용 변경
        userShopCouponService.applyCoupon(userId, dto.getShopCouponId());
        return save;
    }

    // 유저 주문 내역 확인
    public List<Map<String, Object>> findByUserId(Long userId) {
        List<Map<String, Object>> ordersList = ordersRepository.findAllByUserIdEquals(userId);

        List<Map<String, Object>> modifiedOrdersList = new ArrayList<>();

        for (Map<String, Object> orders : ordersList) {
            Map<String, Object> modifiedOrders = new HashMap<>(orders);

            List<Map<String, Object>> ordersProductList = ordersProductService.findAllByOrdersId((Long) orders.get("id"));
            List<Map<String, Object>> modifiedOrdersProductList = new ArrayList<>();

            for (Map<String, Object> ordersProduct : ordersProductList) {
                Map<String, Object> modifiedOrdersProduct = new HashMap<>(ordersProduct);

                List<Map<String, Object>> ordersProductOptionList = ordersProductOptionService.findAllByOrdersProductId((Long) ordersProduct.get("id"));
                modifiedOrdersProduct.put("ordersProductOption", ordersProductOptionList);

                modifiedOrdersProductList.add(modifiedOrdersProduct);
            }

            modifiedOrders.put("ordersProduct", modifiedOrdersProductList);
            modifiedOrdersList.add(modifiedOrders);
        }

        return modifiedOrdersList;
    }


    // 가게별 주문 내역 확인
    public List<Map<String, Object>> findShopOrders(Long shopId) {
        List<Map<String, Object>> ordersList = ordersRepository.findAllByShopIdEquals(shopId);
        List<Map<String, Object>> modifiedOrdersList = new ArrayList<>();

        for (Map<String, Object> orders : ordersList) {
            Map<String, Object> modifiedOrders = new HashMap<>(orders);

            List<Map<String, Object>> ordersProductList = ordersProductService.findAllByOrdersId((Long) orders.get("id"));
            List<Map<String, Object>> modifiedOrdersProductList = new ArrayList<>();

            for (Map<String, Object> ordersProduct : ordersProductList) {
                Map<String, Object> modifiedOrdersProduct = new HashMap<>(ordersProduct);

                List<Map<String, Object>> ordersProductOptionList = ordersProductOptionService.findAllByOrdersProductId((Long)ordersProduct.get("id"));
                modifiedOrdersProduct.put("ordersProductOption", ordersProductOptionList);

                modifiedOrdersProductList.add(modifiedOrdersProduct);
            }

            modifiedOrders.put("ordersProduct", modifiedOrdersProductList);
            modifiedOrdersList.add(modifiedOrders);
        }

        return modifiedOrdersList;
    }

    public Orders findById(Long ordersId) {
        return ordersRepository.findById(ordersId).orElse(null);
    }

    public Page<OrdersReadDto> findAll(String startDt, String endDt, OrderStatus orderStatus, Pageable pageable) {
        return ordersCustomRepository.findAllByUserId(null,startDt,endDt,orderStatus,pageable);
    }

    // 주문 상태 변경
    @Transactional
    public Orders updateOrders(Long ordersId, OrderStatus orderStatus) {
        Orders orders = findById(ordersId);
        orders.setOrderStatus(orderStatus);
        em.flush();
        em.clear();
        return orders;
    }

    // 라이더 배정
    @Transactional
    public Orders updateRiders(Long ordersId, Long riderId) {
        Orders orders = findById(ordersId);
        orders.setRiderId(riderId);
        em.flush();
        em.clear();
        return orders;
    }

    // 가격 계산
    private boolean calculatePrice(OrdersDto dto) {
        // 주문양식 null 이거나 메뉴없이 주문신청할때 에러처리
        if (dto == null || dto.getOrdersProduct() == null) return false;

        // 주문 메뉴 와 옵션들 총 가격
        int total = dto.getOrdersProduct().stream()
                .mapToInt(productDto -> calculateProductTotal(productDto))
                .sum();

        // 총 가격 검증, 할인가격 검증
        if (dto.getPrice() != total || (dto.getShopCouponId() == 0 && dto.getDiscountPrice() != 0) ||
                (dto.getShopCouponId() != 0 && dto.getDiscountPrice() == 0)) {
            return false;
        }

        // 할인쿠폰적용시 실제 할인가격과 주문표의 할인금액 검증
        if (dto.getShopCouponId() != 0L) {
            ShopCoupon shopCoupon = shopCouponService.findById(dto.getShopCouponId());
            int totalDiscount = calculateTotalDiscount(total, shopCoupon);
            if (dto.getDiscountPrice() != totalDiscount) {
                return false;
            }
        }

        return true;
    }

    // 총 메뉴 가격 계산
    private int calculateProductTotal(OrdersDto.OrdersProductDto productDto) {
        Product product = productService.findById(productDto.getProductId());
        int productTotal = product.getPrice() * productDto.getQuantity();

        if (productDto.getProductOptionId().size() != 0) {
            for (Long productOptionId : productDto.getProductOptionId()) {
                ProductOption productOption = productOptionService.findById(productOptionId).orElse(null);
                productTotal += productOption.getPrice();
            }
        }
        return productTotal;
    }

    // 총 할인가격 계산
    private int calculateTotalDiscount(int total, ShopCoupon shopCoupon) {
        if (shopCoupon.isRatio()) {
            return total * shopCoupon.getAmount();
        } else {
            return total - shopCoupon.getAmount();
        }
    }


    // 관리자 페이지 관련
    // 대시보드
    public Integer countAllByOrderStatusEquals(OrderStatus orderStatus) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return ordersRepository.countAllByOrderStatusEquals(orderStatus, start, end);
    }

    public List countAllByCreatedDtBetween() {
        LocalDate today = LocalDate.now();

        List data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate day = today.minusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            String formattedDay = day.format(formatter);

            LocalDateTime start = day.atStartOfDay();
            LocalDateTime end = day.atTime(LocalTime.MAX);
            Integer count = ordersRepository.countAllByCreatedDtBetween(start, end);

            Map<String, Object> map = new HashMap<>();
            map.put("x", formattedDay);
            map.put("y", count);
            data.add(map);
        }

        return data;
    }

    public List countFiveDayAmount() {
        LocalDate today = LocalDate.now();

        List data = new ArrayList();
        for (int i = 0; i < 5; i++) {
            LocalDate day = today.minusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            String formattedDay = day.format(formatter);

            LocalDateTime start = day.atStartOfDay();
            LocalDateTime end = day.atTime(LocalTime.MAX);
            System.out.println(start+"스타트");
            System.out.println(end+"엔드");
            Integer price = ordersRepository.getTotalAmount(start, end).orElse(0);

            Map<Object, Object> map = new HashMap<>();
            map.put("x", formattedDay);
            map.put(i, price);
            data.add(map);
        }
        return data;
    }

    // 회원관리 페이지 통합주문내역  주문상품통계
    public Map<String, Object> findOneUserStatistics(Long userId) {
        Integer totalOrders = ordersRepository.countAllByUserIdEquals(userId);
        Integer waiting = ordersRepository.countAllByUserIdAndOrderStatusEquals(userId, OrderStatus.WAITING);
        Integer complete = ordersRepository.countAllByUserIdAndOrderStatusEquals(userId, OrderStatus.COMPLETE);
        Integer change = ordersRepository.countAllByUserIdAndOrderStatusEquals(userId, OrderStatus.CHANGE);
        Integer cancel = ordersRepository.countAllByUserIdAndOrderStatusEquals(userId, OrderStatus.CANCEL);
        Integer totalPrice = ordersRepository.getUserOrdersTotal(userId, OrderStatus.COMPLETE).orElse(0);

        Map<String, Object> map = new HashMap<>();
        map.put("totalOrders", totalOrders);
        map.put("waiting", waiting);
        map.put("complete", complete);
        map.put("change", change);
        map.put("cancel", cancel);
        map.put("totalPrice", totalPrice);

        return map;
    }

    // 회원관리 페이지 통합주문내역  통합주문조회
    public Map<String, Object> findUserOrdersHistory(Long userId, String startDt, String endDt,
                                                     OrderStatus orderStatus, Pageable pageable) {

        Page<OrdersReadDto> pageObject = ordersCustomRepository.findAllByUserId(userId, startDt, endDt, orderStatus, pageable);
        List<OrdersReadDto> content = pageObject.getContent();
        long totalElements = pageObject.getTotalElements();

        Map<String, Object> result = new HashMap<>();
        result.put("ordersList", content);
        result.put("totalItem", totalElements);
        return result;
    }

    // 상점관리 페이지 통합주문내역
    public Map<String, Object> findShopStatistics(Long shopId) {
        Integer totalOrders = ordersRepository.countAllByShopIdEquals(shopId);
        Integer waiting = ordersRepository.countAllByShopIdAndOrderStatusEquals(shopId, OrderStatus.WAITING);
        Integer complete = ordersRepository.countAllByShopIdAndOrderStatusEquals(shopId, OrderStatus.COMPLETE);
        Integer change = ordersRepository.countAllByShopIdAndOrderStatusEquals(shopId, OrderStatus.CHANGE);
        Integer cancel = ordersRepository.countAllByShopIdAndOrderStatusEquals(shopId, OrderStatus.CANCEL);
        Integer totalPrice = ordersRepository.getShopOrdersTotal(shopId, OrderStatus.COMPLETE).orElse(0);

        Map<String, Object> map = new HashMap<>();
        map.put("totalOrders", totalOrders);
        map.put("waiting", waiting);
        map.put("complete", complete);
        map.put("change", change);
        map.put("cancel", cancel);
        map.put("totalPrice", totalPrice);

        return map;
    }

    // 상점관리 페이지 통합주문내역  통합주문조회
    public Map<String, Object> findShopOrdersHistory(Long shopId, String startDt, String endDt,
                                                     OrderStatus orderStatus, Pageable pageable) {

        Page<OrdersReadDto> pageObject = ordersCustomRepository.findAllByShopId(shopId, startDt, endDt, orderStatus, pageable);
        List<OrdersReadDto> content = pageObject.getContent();
        long totalElements = pageObject.getTotalElements();

        Map<String, Object> result = new HashMap<>();
        result.put("ordersList", content);
        result.put("totalItem", totalElements);
        return result;
    }

    // 라이더관리 페이지 배달수행정보  수행내역 통계
    public Map<String, Object> findRiderStatistics(Long riderId) {
        Integer totalOrders = ordersRepository.countAllByRiderIdEquals(riderId);
        Integer waiting = ordersRepository.countAllByRiderIdAndOrderStatusEquals(riderId, OrderStatus.WAITING);
        Integer complete = ordersRepository.countAllByRiderIdAndOrderStatusEquals(riderId, OrderStatus.COMPLETE);
        Integer change = ordersRepository.countAllByRiderIdAndOrderStatusEquals(riderId, OrderStatus.CHANGE);
        Integer cancel = ordersRepository.countAllByRiderIdAndOrderStatusEquals(riderId, OrderStatus.CANCEL);
        Integer totalPrice = ordersRepository.getRiderOrdersTotal(riderId, OrderStatus.COMPLETE).orElse(0);

        Map<String, Object> map = new HashMap<>();
        map.put("totalOrders", totalOrders);
        map.put("waiting", waiting);
        map.put("complete", complete);
        map.put("change", change);
        map.put("cancel", cancel);
        map.put("totalPrice", totalPrice);

        return map;
    }

    // 라이더 관리 페이지 배달 수행정보
    public Map<String, Object> findRiderOrdersHistory(Long shopId, String startDt, String endDt,
                                                      OrderStatus orderStatus, Pageable pageable) {
        Page<OrdersReadDto> pageObject = ordersCustomRepository.findAllByRiderId(shopId, startDt, endDt, orderStatus, pageable);
        List<OrdersReadDto> content = pageObject.getContent();
        long totalElements = pageObject.getTotalElements();

        Map<String, Object> result = new HashMap<>();
        result.put("ordersList", content);
        result.put("totalItem", totalElements);
        return result;
    }
}
