package com.oneday.api.repository;


import com.oneday.api.model.Complain;
import com.oneday.api.model.base.ComplainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ComplainRepository extends JpaRepository<Complain, Long> {

    @Query("SELECT COUNT(c) FROM Complain c WHERE c.createdDt BETWEEN :start AND :end")
    Optional<Integer> countAllDayComplain( @Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);


    @Query("SELECT COUNT(c) FROM Complain c WHERE c.complainCategory = :complainCategory AND c.createdDt BETWEEN :start AND :end")
    Optional<Integer> countAllWeekComplainByCategory(@Param("complainCategory") ComplainCategory complainCategory,
                                             @Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);



}
