package com.oneday.api.repository;

import com.oneday.api.model.UserBasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserBasketProductRepository extends JpaRepository<UserBasketProduct, Long> {

    @Query(value = "select ubp.id, p.name, p.product_category, p.price, ubp.quantity, ubp.shop_id " +
            "from user_basket_product ubp " +
            "left join product p on p.id = ubp.product_id " +
            "where ubp.user_id = :userId", nativeQuery = true)
    List<Map<String,Object>> findAllByUserIdEquals(@Param("userId") Long userId);
}
