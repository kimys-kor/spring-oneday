package com.oneday.api.repository;

import com.oneday.api.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query(value = "select * " +
            "from product_option " +
            "where product_id = :productId",nativeQuery = true)
    List<Map<String, Object>> findByProductIdEquals(@Param("productId") Long productId);

    List<ProductOption> findByProductIdIn(List<Long> productIdList);

    // id만 리스트로 추출
    @Query(value = "select id " +
            "from product_option " +
            "where product_id = :productId",nativeQuery = true)
    List<Long> findIdListByProductIdEquals(@Param("productId") Long productId);
}
