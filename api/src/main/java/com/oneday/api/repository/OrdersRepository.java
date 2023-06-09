package com.oneday.api.repository;

import com.oneday.api.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(value = "select * " +
            "from orders " +
            "where user_id = :userId " +
            "ORDER BY created_dt DESC ",nativeQuery = true)
    List<Map<String, Object>> findAllByUserIdEquals(@Param("userId") Long userId);

    // 시장별 주문내역
    @Query(value = "select * " +
            "from orders " +
            "where shop_id = :shopId " +
            "ORDER BY created_dt DESC ",nativeQuery = true)
    List<Map<String, Object>> findAllByShopIdEquals(@Param("shopId") Long shopId);
}
