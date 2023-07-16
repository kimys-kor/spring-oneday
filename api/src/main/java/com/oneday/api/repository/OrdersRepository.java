package com.oneday.api.repository;

import com.oneday.api.model.Orders;
import com.oneday.api.model.base.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    Integer countAllByOrderStatusEquals(OrderStatus orderStatus);

    Integer countAllByCreatedDtBetween(LocalDateTime start, LocalDateTime end);


    @Query("SELECT SUM(o.price) FROM Orders o WHERE o.createdDt between :start AND :end")
    Optional<Integer> getTotalAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
