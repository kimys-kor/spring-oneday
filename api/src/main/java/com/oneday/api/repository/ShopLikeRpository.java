package com.oneday.api.repository;

import com.oneday.api.model.ShopLike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ShopLikeRpository extends JpaRepository<ShopLike, Long> {

    ShopLike findByShopIdAndUserIdEquals(@Param("shopId")Long shopId,
                                         @Param("userId")Long userId);

    @Transactional
    void deleteByShopIdAndUserIdEquals(@Param("shopId") Long shopId,
                                       @Param("userId")Long userId);
}
