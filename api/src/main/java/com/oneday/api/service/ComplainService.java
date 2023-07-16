package com.oneday.api.service;

import com.oneday.api.model.base.ComplainCategory;
import com.oneday.api.repository.ComplainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ComplainService {

    private final ComplainRepository complainRepository;

    public Integer countDayComplain() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return complainRepository.countAllDayComplain(start, end).orElse(0);
    }

    public Integer countWeekComplain(ComplainCategory complainCategory) {
        LocalDate today = LocalDate.now();
        LocalDate beforeWeek = today.minusDays(7);
        LocalDateTime start = beforeWeek.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);
        return complainRepository.countAllWeekComplainByCategory(complainCategory, start, end).orElse(0);
    }

}
