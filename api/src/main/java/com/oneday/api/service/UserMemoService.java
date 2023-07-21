package com.oneday.api.service;

import com.oneday.api.model.UserMemo;
import com.oneday.api.repository.UserMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMemoService {

    private final UserMemoRepository userMemoRepository;

    public UserMemo save(Long userId, String content) {
        UserMemo userMemo = UserMemo.builder()
                .content(content)
                .userId(userId)
                .build();
        return userMemoRepository.save(userMemo);
    }

    public List<UserMemo> findByUserId(Long userId) {
        return userMemoRepository.findByUserIdEquals(userId);
    }

    public void deleteByIdEquals(Long userMemoId) {
        userMemoRepository.deleteById(userMemoId);
    }

}
