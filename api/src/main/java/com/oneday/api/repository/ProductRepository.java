package com.oneday.api.repository;

import com.oneday.api.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // shopId로 찾기
    @Query(value = "select * " +
            "from product " +
            "where shop_id = :shopId",nativeQuery = true)
    List<Product> findAllByShopIdEqualsForDelete(@Param("shopId") Long shopId);

    @Query(value = "select * " +
            "from product " +
            "where shop_id = :shopId",nativeQuery = true)
    List<Product> findAllByShopIdEqualsForAdmin(@Param("shopId") Long shopId, Pageable pageable);



    @Query(value = "select * " +
            "from product " +
            "where product_category = :productCategory",nativeQuery = true)
    List<Map<String, Object>> findAllByCategoryEquals(@Param("productCategory") String productCategory);

    void deleteAllByShopIdEquals(@Param("shopId") Long shopId);
}
