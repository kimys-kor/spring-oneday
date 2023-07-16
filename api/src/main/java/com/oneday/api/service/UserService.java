package com.oneday.api.service;

import com.oneday.api.model.User;
import com.oneday.api.model.dto.UserDto;
import com.oneday.api.repository.UserCustomRepository;
import com.oneday.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final UserCustomRepository userCustomRepository;

    public User findByEmail(String email) {
        User byId = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        return byId;
    }

    public Optional<User> findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId;
    }

    public void join(UserDto userDto) {
        String encPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encPassword);
        User user = new User(userDto.getEmail(), userDto.getPassword(), userDto.getNickname(), userDto.getPhoneNum());
        userRepository.save(user);
    }

    public Page<UserDto> findAll(Pageable pageable) {
        return userCustomRepository.findAll(pageable);
    }

    public List countAllByCreatedDtBetween() {
        LocalDate today = LocalDate.now();

        List data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate day = today.minusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            String formattedDay = day.format(formatter);

            LocalDateTime start = day.atStartOfDay();
            LocalDateTime end = day.atTime(LocalTime.MAX);
            Integer count = userRepository.countAllByCreatedDtBetween(start, end);

            Map<String, Object> map = new HashMap<>();
            map.put("x", formattedDay);
            map.put("y", count);
            data.add(map);
        }

        return data;
    }


}
