package com.oneday.api.common.security;

import com.oneday.api.model.User;
import com.oneday.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

// 시큐리티 설정에서 loginProcessingUrl("/login")을 설정했기 때문에 login요청이 오면 자동으로 loadUserByUsername가 실행됨
@Component
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserService userService;

    // 시큐리티 Session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loaduser실행");
        User findOne = userService.findByEmail(email);
        return new PrincipalDetails(findOne);
    }
}
