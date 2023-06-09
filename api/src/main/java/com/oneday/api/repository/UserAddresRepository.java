package com.oneday.api.repository;

import com.oneday.api.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserAddresRepository extends JpaRepository<UserAddress, Long> {

    @Query(value = "select * " +
            "from user_address " +
            "where user_id = :userId", nativeQuery = true)
    List<Map<String, Object>> findByUserIdEquals(@Param("userId") Long userId);
}
