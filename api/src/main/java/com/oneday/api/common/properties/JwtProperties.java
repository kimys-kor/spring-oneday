package com.oneday.api.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationProperties(prefix = "app.jwt")
@ConfigurationPropertiesBinding
public record JwtProperties(
        String secretKey,
        Long timeToLive
) {
    public JwtProperties { // compact constructor
        // this -> null yet.
        if (timeToLive == null) {
            timeToLive = 1_800_000L;
        }
    }
}
