package com.oneday.api.repository;

import com.oneday.api.model.ShopCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopCouponRepository extends JpaRepository<ShopCoupon, Long> {

    ShopCoupon findByShopIdEqualsAndTitleEquals(@Param("shopId")Long shopId,
                                                @Param("title")String title);


    List<ShopCoupon> findAllByShopIdEquals(Long shopId);
}
