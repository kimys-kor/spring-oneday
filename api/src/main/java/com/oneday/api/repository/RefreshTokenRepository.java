package com.oneday.api.repository;

import com.oneday.api.model.RefreshTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    boolean existsByRefreshTokenAndEmail(String refreshToken, String email);

    @Transactional
    void deleteByEmailEquals(@Param("email") String email);
}
