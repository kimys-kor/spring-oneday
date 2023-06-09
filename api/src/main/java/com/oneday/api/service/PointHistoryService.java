package com.oneday.api.service;

import com.oneday.api.model.PointHistory;
import com.oneday.api.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;

    public PointHistory save(PointHistory pointHistory) {
        return pointHistoryRepository.save(pointHistory);
    }
}
