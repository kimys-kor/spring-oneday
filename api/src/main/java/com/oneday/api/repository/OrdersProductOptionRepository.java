package com.oneday.api.repository;

import com.oneday.api.model.OrdersProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OrdersProductOptionRepository extends JpaRepository<OrdersProductOption, Long> {

    @Query(value = "select mo.menu_id, mo.name, mo.price " +
            "from orders_product_option opo " +
            "left join product_option po on po.id = opo.orders_product_id " +
            "where orders_product_id = :ordersProductId", nativeQuery = true)
    List<Map<String, Object>> findAllByOrdersProductId(Long ordersProductId);
}
