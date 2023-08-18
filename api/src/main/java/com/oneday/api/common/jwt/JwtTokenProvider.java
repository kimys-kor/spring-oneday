package com.oneday.api.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.oneday.api.common.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public final class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(Long userId, String email) {
        String jwtToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis()+ jwtProperties.expirationTime()))
                .withClaim("id", userId)
                .withClaim("username", email)
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));

        return jwtToken;
    }

    public String resolveToken(String token) {
        return JWT.require(Algorithm.HMAC512(jwtProperties.secretKey()))
                .build()
                .verify(token)
                .getClaim("username")
                .asString();
    }

}
