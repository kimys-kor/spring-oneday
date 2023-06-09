package com.oneday.api.repository;

import com.oneday.api.model.ShopAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ShopAddressRepository extends JpaRepository<ShopAddress, Long> {

    @Query(value = "select * " +
            "from shop_address " +
            "where shop_id = :shopId", nativeQuery = true)
    List<Map<String, Object>> findByShopIdEquals(@Param("shopId") Long shopId);
}
