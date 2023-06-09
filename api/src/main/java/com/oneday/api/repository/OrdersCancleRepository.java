package com.oneday.api.repository;

import com.oneday.api.model.OrdersCancle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface OrdersCancleRepository extends JpaRepository<OrdersCancle, Long> {
    OrdersCancle findByUserIdAndOrdersIdEquals(@Param("userId") Long userId,
                                              @Param("ordersId") Long ordersId);
}
