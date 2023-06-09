package com.oneday.api.repository;

import com.oneday.api.model.ShopReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopReviewRepository extends JpaRepository<ShopReview, Long> {

    @Query(value = "select * " +
            "from shop_review " +
            "where shop_id = :shopId " +
            "ORDER BY created_dt DESC ",nativeQuery = true)
    List<ShopReview> findAllByShopIdEquals(@Param("shopId") Long shopid);



    @Query(value = "delete " +
            "from shop_review " +
            "where id = :shopReviewId",nativeQuery = true)
    void deleteOne(@Param("shopReviewId") Long shopReviewId);

    @Query(value = "select FORMAT(AVG(score), 2) AS Average " +
            "FROM shop_review sr " +
            "WHERE sr.shop_id = :shopId "
            ,nativeQuery = true)
    Float AvgReviewScoreByShopIdEquals(@Param("shopId") Long shopId);
}
