package com.oneday.api.service;

import com.oneday.api.model.Member;
import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.repository.MemberCustomRepository;
import com.oneday.api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberCustomRepository memberCustomRepository;

    public Member findByEmail(String email) {
        Member byId = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));
        return byId;
    }

    public Optional<Member> findById(Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        return byId;
    }

    public void join(MemberDto memberDto) {
        String encPassword = bCryptPasswordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encPassword);
        Member member = new Member(memberDto.getEmail(), memberDto.getPassword(), memberDto.getNickname(), memberDto.getPhoneNum());
        memberRepository.save(member);
    }

    public Page<MemberDto> findAll(Pageable pageable) {
        return memberCustomRepository.findAll(pageable);
    }


}
