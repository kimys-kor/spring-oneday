package com.oneday.api.service;

import com.oneday.api.common.exception.AuthenticationErrorCode;
import com.oneday.api.common.exception.AuthenticationException;
import com.oneday.api.common.jwt.JwtTokenProvider;
import com.oneday.api.model.RefreshTokenEntity;
import com.oneday.api.repository.RefreshTokenRepository;
import com.oneday.api.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public void save(String email, String refreshToken) {

        RefreshTokenEntity tokenEntity = RefreshTokenEntity.builder()
                .refreshToken(refreshToken)
                .email(email)
                .build();

        refreshTokenRepository.deleteByEmailEquals(email);
        refreshTokenRepository.save(tokenEntity);
    }

    public String refresh(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) throw new AuthenticationException(AuthenticationErrorCode.UNKNOWN_ERROR);

        String oldRefreshToken = Arrays.stream(cookies)
            .filter(eachCookie -> "refresh_token".equals(eachCookie.getName()))
            .findAny()
            .orElseThrow()
            .getValue();

        RefreshTokenEntity tokenEntity = refreshTokenRepository.findById(oldRefreshToken).orElseThrow(
                AuthenticationErrorCode.UNKNOWN_REFRESH_TOKEN::defaultException);

        String email = tokenEntity.getEmail();
        Long userId = userRepository.findByEmail(email).orElseThrow(
                AuthenticationErrorCode.AUTHENTICATION_FAILED::defaultException).getId();

        String accessToken = jwtTokenProvider.generateToken(userId, email);

        return accessToken;
    }

    public boolean existsByRefreshTokenAndEmail(String refreshToken, String email) {
        return refreshTokenRepository.existsByRefreshTokenAndEmail(refreshToken, email);
    }
}
