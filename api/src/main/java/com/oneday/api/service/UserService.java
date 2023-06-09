package com.oneday.api.service;

import com.oneday.api.model.User;
import com.oneday.api.model.dto.MemberDto;
import com.oneday.api.repository.MemberCustomRepository;
import com.oneday.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final MemberCustomRepository memberCustomRepository;

    public User findByEmail(String email) {
        User byId = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        return byId;
    }

    public Optional<User> findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId;
    }

    public void join(MemberDto memberDto) {
        String encPassword = bCryptPasswordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encPassword);
        User user = new User(memberDto.getEmail(), memberDto.getPassword(), memberDto.getNickname(), memberDto.getPhoneNum());
        userRepository.save(user);
    }

    public Page<MemberDto> findAll(Pageable pageable) {
        return memberCustomRepository.findAll(pageable);
    }


}
