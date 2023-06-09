package com.oneday.api.service;

import com.oneday.api.model.ShopCoupon;
import com.oneday.api.repository.ShopCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCouponService {

    private final ShopCouponRepository shopCouponRepository;

    public ShopCoupon save(ShopCoupon shopCoupon) {
        return shopCouponRepository.save(shopCoupon);
    }


    public ShopCoupon update(Long shopCouponId, Long shopId, String title, String content, LocalDateTime startDt,
                             LocalDateTime endDt, boolean isRatio, int amount) {

        ShopCoupon byId = findById(shopCouponId);
        byId.setTitle(title);
        byId.setContent(content);
        byId.setStartDt(startDt);
        byId.setEndDt(endDt);
        byId.setRatio(isRatio);
        byId.setAmount(amount);
        return save(byId);
    }

    // 아이디로 쿠폰 찾기
    public ShopCoupon findById(Long shopCouponId) {
        return shopCouponRepository.findById(shopCouponId).orElse(null);
    }

    public ShopCoupon findByShopIdEqualsAndTitleEquals(Long shopId, String title){
        return shopCouponRepository.findByShopIdEqualsAndTitleEquals(shopId, title);
    }

    // 가게별 쿠폰 목록
    public List<ShopCoupon> findAllByShopIdEquals(Long shopId) {
        return shopCouponRepository.findAllByShopIdEquals(shopId);
    }

    public void delete(Long shopCouponId) {
        ShopCoupon byId = findById(shopCouponId);
        shopCouponRepository.delete(byId);
    }
}
