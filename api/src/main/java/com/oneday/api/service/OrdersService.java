package com.oneday.api.service;

import com.oneday.api.model.*;
import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.OrdersDto;
import com.oneday.api.model.dto.OrdersReadDto;
import com.oneday.api.repository.OrdersCustomRepository;
import com.oneday.api.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersService {

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

        // 주문에 속한 여러 메뉴들 저장
        List<OrdersDto.OrdersProductDto> ordersProduct = dto.getOrdersProduct();
        for (OrdersDto.OrdersProductDto ordersProductDto : ordersProduct) {
            OrdersProduct save1 = ordersProductService.save(save.getId(), ordersProductDto.getProductId(), ordersProductDto.getQuantity());

            // 주문에 속한 메뉴에 속한 여러 옵션들 저장
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
        return ordersCustomRepository.findAll(startDt,endDt,orderStatus,pageable);
    }

    // 주문 상태 변경
    public Orders updateOrders(Long ordersId, OrderStatus orderStatus) {
        Orders byId = findById(ordersId);
        byId.setOrderStatus(orderStatus);
        return ordersRepository.save(byId);
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
        if (dto.getShopCouponId() != 0) {
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
        for (Long productOptionId : productDto.getProductOptionId()) {
            ProductOption productOption = productOptionService.findById(productOptionId);
            productTotal += productOption.getPrice();
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
}
