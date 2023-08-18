package com.oneday.api.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties(prefix = "app.jwt")
@ConfigurationPropertiesBinding
public record JwtProperties(
        String secretKey,
        Long expirationTime,
        String tokenPrefix,
        String headerString

) {
    public JwtProperties { // compact constructor
        // this -> null yet.
        if (expirationTime == null) {
            expirationTime = 1_200_000L;
        }
    }
}
