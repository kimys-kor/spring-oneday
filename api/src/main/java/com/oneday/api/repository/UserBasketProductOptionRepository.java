package com.oneday.api.repository;

import com.oneday.api.model.UserBasketProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserBasketProductOptionRepository extends JpaRepository<UserBasketProductOption, Integer> {

    @Query(value = "select ubpo.id, po.product_id, po.name, po.price " +
            "from user_basket_product_option ubpo " +
            "left join product_option po on po.id = ubpo.product_option_id " +
            "where user_basket_product_id = :userBasketProductId", nativeQuery = true)
    List<Map<String,Object>> findAllByUserBasketProductId(@Param("userBasketProductId") Long userBasketProductId);

    List<UserBasketProductOption> findAllByUserBasketProductIdEquals(@Param("userBasketProductId") Long userBasketProductId);
}
