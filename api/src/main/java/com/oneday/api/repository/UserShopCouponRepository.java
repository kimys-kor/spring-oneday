package com.oneday.api.repository;

import com.oneday.api.model.UserShopCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserShopCouponRepository extends JpaRepository<UserShopCoupon, Long> {

    @Query(value = "SELECT sc.id AS shopCouponId, sc.content, sc.end_dt AS endDt, sc.ratio, sc.shop_id, sc.start_dt, sc.title " +
            "FROM user_shop_coupon usc " +
            "LEFT JOIN shop_coupon sc ON sc.id = usc.shop_coupon_id " +
            "WHERE usc.user_id = :userId AND usc.is_used = false", nativeQuery = true)
    List<Map<String,Object>> findAllByUserIdEquals(@Param("userId") Long userId);

    @Query(value = "select sc.id AS shopCouponId, sc.content, sc.end_dt, sc.ratio, sc.shop_id, sc.start_dt, sc.end_dt, sc.title " +
            "from user_shop_coupon usc " +
            "left join shop_coupon sc on sc.id = usc.shop_coupon_id " +
            "where usc.user_id = :userId AND sc.shop_id = :shopId AND usc.is_used = false ", nativeQuery = true)
    List<Map<String,Object>> findAllByUserIdAndShopIdEquals(@Param("userId") Long userId,
                                                            @Param("shopId") Long shopId);


    @Query(value = "select sc.id AS shopCouponId, sc.content, sc.end_dt, sc.ratio, sc.shop_id, sc.start_dt, sc.end_dt, sc.title " +
            "from user_shop_coupon usc " +
            "left join shop_coupon sc on sc.id = usc.shop_coupon_id " +
            "where usc.user_id = :userId AND sc.shop_id = :shopId AND usc.is_used = false ", nativeQuery = true)
    List<Map<String,Object>> findAllByUserIdAndShopIdUsedFlagFalse(@Param("userId") Long userId,
                                                                   @Param("shopId") Long shopId);


    UserShopCoupon findByUserIdAndShopCouponIdEquals(Long userId, Long shopCouponId);
}
