package com.oneday.api.common.security;

import com.oneday.api.model.Member;
import com.oneday.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

// 시큐리티 설정에서 loginProcessingUrl("/login")을 설정했기 때문에 login요청이 오면 자동으로 loadUserByUsername가 실행됨
@Component
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final MemberService memberService;

    // 시큐리티 Session(내부 Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> findOne = memberService.findByEmail(email);
        Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));
        return new MyUserDetail(member);
    }
}
