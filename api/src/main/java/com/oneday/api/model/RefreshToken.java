package com.oneday.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    private String id;

    private String ip;

    private Collection<? extends GrantedAuthority> authorities;

    private String refreshToken;
}