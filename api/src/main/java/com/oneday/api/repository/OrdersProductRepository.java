package com.oneday.api.repository;

import com.oneday.api.model.OrdersProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OrdersProductRepository extends JpaRepository<OrdersProduct, Long> {

    @Query(value = "select * " +
            "from orders_product " +
            "where orders_id = :ordersId", nativeQuery = true)
    List<Map<String, Object>> findAllByOrdersId(Long ordersId);

}
