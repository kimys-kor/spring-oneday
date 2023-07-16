package com.oneday.api.repository;


import com.oneday.api.model.Complain;
import com.oneday.api.model.base.ComplainCategory;
import com.oneday.api.model.base.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ComplainRepository extends JpaRepository<Complain, Long> {

    Integer countAllByComplainCategoryEquals(ComplainCategory complainCategory);

    Integer countAllByCreatedDtBetween(LocalDateTime start, LocalDateTime end);


    @Query("SELECT SUM(o.price) FROM Orders o WHERE o.createdDt between :start AND :end")
    Optional<Integer> getTotalAmount(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
