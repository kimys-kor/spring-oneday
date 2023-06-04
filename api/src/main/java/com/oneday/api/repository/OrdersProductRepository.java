package com.oneday.api.repository;

import com.oneday.api.model.OrdersProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersProductRepository extends JpaRepository<OrdersProduct, Long> {
}
