package com.oneday.api.service;

import com.oneday.api.model.ShopCoupon;
import com.oneday.api.model.UserShopCoupon;
import com.oneday.api.repository.UserShopCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserShopCouponService {

    private final UserShopCouponRepository userShopCouponRepository;
    private final ShopCouponService shopCouponService;

    public UserShopCoupon save(Long userId, Long shopCouponId) {

        // 한장만 발급 제한
        UserShopCoupon findCoupon = findByUserIdAndShopCouponIdEquals(userId, shopCouponId);
        if (findCoupon != null) {
            return null;
        } else {
            UserShopCoupon userShopCoupon = new UserShopCoupon(userId, shopCouponId);
            return userShopCouponRepository.save(userShopCoupon);
        }
    }

    // 쿠폰 사용 하기
    public UserShopCoupon applyCoupon(Long userId, Long shopCouponId) {
        if(shopCouponId == 0) return null;
        // 발급시 한장만 생성 제한 했으므로 유니크 보장
        UserShopCoupon byId = findByUserIdAndShopCouponIdEquals(userId,shopCouponId);

        LocalDateTime now = LocalDateTime.now();
        ShopCoupon shopCoupon = shopCouponService.findById(byId.getShopCouponId());

        // 사용 기한 체크
        if (shopCoupon.getStartDt().compareTo(now) > 0 || shopCoupon.getEndDt().compareTo(now) < 0) {
            return null;
        } else {
            byId.setUsed(true);
            return userShopCouponRepository.save(byId);
        }
    }

    private UserShopCoupon findByUserIdAndShopCouponIdEquals(Long userId,Long shopCouponId) {
        return userShopCouponRepository.findByUserIdAndShopCouponIdEquals(userId, shopCouponId);
    }

    // 기간 지난 쿠폰 삭제 하기


    // 가게 상관없이 유저가 발급받은 쿠폰 리스트
    public List<Map<String,Object>> findAllByUserIdEquals(Long userId) {
        return userShopCouponRepository.findAllByUserIdEquals(userId);
    }

    // 가게별 유저가 발급받은 쿠폰 리스트
    public List<Map<String,Object>> findAllByUserIdAndShopIdEquals(Long userId,Long shopId) {
        return userShopCouponRepository.findAllByUserIdAndShopIdEquals(userId, shopId);
    }

    public UserShopCoupon findBy(Long userShopCouponId) {
        return userShopCouponRepository.findById(userShopCouponId).orElse(null);
    }
}
