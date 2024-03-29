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

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderStatus = :orderStatus AND o.createdDt between :start AND :end")
    Integer countAllByOrderStatusEquals(@Param("orderStatus") OrderStatus orderStatus,
                                        @Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

    Integer countAllByCreatedDtBetween(LocalDateTime start, LocalDateTime end);


    @Query("SELECT SUM(o.price) FROM Orders o WHERE o.createdDt between :start AND :end")
    Optional<Integer> getTotalAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    Integer countAllByUserIdEquals(Long userId);
    Integer countAllByShopIdEquals(Long shopId);
    Integer countAllByRiderIdEquals(Long riderId);
    Integer countAllByUserIdAndOrderStatusEquals(Long userId, OrderStatus orderStatus);
    Integer countAllByShopIdAndOrderStatusEquals(Long shopId, OrderStatus orderStatus);
    Integer countAllByRiderIdAndOrderStatusEquals(Long riderId, OrderStatus orderStatus);

    @Query("SELECT SUM(o.price) FROM Orders o WHERE o.userId = :userId AND o.orderStatus = :orderStatus ")
    Optional<Integer> getUserOrdersTotal(@Param("userId") Long userId, @Param("orderStatus") OrderStatus orderStatus);

    @Query("SELECT SUM(o.price) FROM Orders o WHERE o.shopId = :shopId AND o.orderStatus = :orderStatus ")
    Optional<Integer> getShopOrdersTotal(@Param("shopId") Long shopId, @Param("orderStatus") OrderStatus orderStatus);

    @Query("SELECT SUM(o.price) FROM Orders o WHERE o.riderId = :riderId AND o.orderStatus = :orderStatus ")
    Optional<Integer> getRiderOrdersTotal(@Param("riderId") Long riderId, @Param("orderStatus") OrderStatus orderStatus);

}
