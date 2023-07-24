package com.oneday.api.service;

import com.oneday.api.model.User;
import com.oneday.api.model.UserMemo;
import com.oneday.api.model.base.UserGrade;
import com.oneday.api.model.dto.UserDetailDto;
import com.oneday.api.model.dto.UserDto;
import com.oneday.api.model.dto.UserReadDto;
import com.oneday.api.repository.UserCustomRepository;
import com.oneday.api.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    @PersistenceContext
    EntityManager em;

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

    // 관리자 페이지 유저 리스트
    public Map<String, Object> findAll(Pageable pageable) {
        Page<UserReadDto> pageObject = userCustomRepository.findAll(pageable);

        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("users",pageObject.getContent());
        result.put("totalItem", pageObject.getTotalElements());

        return result;
    }

    // 관리자 페이지 유저 상세
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

    // 유저 비밀번호 업데이트
    @Transactional
    public String updatePassword(Long userId) {
        Random random = new Random();
        int temp = 0;
        String tempNum = "";
        int size    = 6;
        String resultNum = "";

        for (int i=0; i<size; i++) {
            temp = random.nextInt(9);
            tempNum =  Integer.toString(temp);
            resultNum += tempNum;
        }
        String encPassword = bCryptPasswordEncoder.encode(resultNum);
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        user.setPassword(encPassword);
        em.flush();
        em.clear();
        return resultNum;
    }

    // 유저 포인트 추가
    @Transactional
    public void addPoint(Long userId, Integer point) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        user.setPoint(user.getPoint()+point);
        em.flush();
        em.clear();
    }

    // 유저 정보 수정
    @Transactional
    public void updateInfo(Long userId, String userNickname, String userGrade) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        UserGrade grade = UserGrade.of(userGrade);

        user.setNickname(userNickname);
        user.setGrade(grade);
        em.flush();
        em.clear();

    }
}
