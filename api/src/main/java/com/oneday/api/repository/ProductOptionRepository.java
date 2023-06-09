package com.oneday.api.repository;

import com.oneday.api.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query(value = "select * " +
            "from product_option " +
            "where product_id = :productId",nativeQuery = true)
    List<Map<String, Object>> findByProductIdEquals(Long productId);
}
