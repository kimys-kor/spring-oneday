package com.oneday.api.service;

import com.oneday.api.model.PointHistory;
import com.oneday.api.repository.PointHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointHistoryService {

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    public PointHistory save(PointHistory pointHistory) {
        return pointHistoryRepository.save(pointHistory);
    }
}
