package com.oneday.api.repository;


import com.oneday.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Integer countAllByCreatedDtBetween(LocalDateTime start, LocalDateTime end);

}
