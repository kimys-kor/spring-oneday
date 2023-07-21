package com.oneday.api.repository;

import com.oneday.api.model.UserMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMemoRepository extends JpaRepository<UserMemo, Long> {

    List<UserMemo> findByUserIdEquals(@Param("userId") Long userId);


}
