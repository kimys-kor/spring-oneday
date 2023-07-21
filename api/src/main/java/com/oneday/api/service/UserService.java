package com.oneday.api.service;

import com.oneday.api.model.User;
import com.oneday.api.model.UserMemo;
import com.oneday.api.model.dto.UserDetailDto;
import com.oneday.api.model.dto.UserDto;
import com.oneday.api.model.dto.UserReadDto;
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
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final UserCustomRepository userCustomRepository;

    private final UserMemoService userMemoService;

    public User findByEmail(String email) {
        User byId = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        return byId;
    }

    public void updateLastLogin(Long userId, LocalDateTime time) {
        userRepository.updateLastLogin(userId, time);
    }

    public Map<String,Object> findById(Long userId) {
        UserDetailDto userDetailDto = userCustomRepository.findById(userId);
        List<UserMemo> userMemoList = userMemoService.findByUserId(userId);

        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("userDetail", userDetailDto);
        result.put("userMemoList", userMemoList);
        return result;
    }

    public void join(UserDto userDto) {
        String encPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encPassword);
        User user = new User(userDto.getEmail(), userDto.getPassword(), userDto.getNickname(), userDto.getPhoneNum());
        userRepository.save(user);
    }

    public Map<String, Object> findAll(Pageable pageable) {
        Page<UserReadDto> pageObject = userCustomRepository.findAll(pageable);

        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("users",pageObject.getContent());
        result.put("totalItem", pageObject.getTotalElements());

        return result;
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
