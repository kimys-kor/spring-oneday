package com.oneday.api.service;

import com.oneday.api.model.OrdersCancle;
import com.oneday.api.repository.OrdersCancleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersCancleService {

    private final OrdersCancleRepository ordersCancleRepository;

    public OrdersCancle save(Long userId, Long ordersId) {
        OrdersCancle orderCancle = OrdersCancle.builder()
                .userId(userId)
                .ordersId(ordersId)
                .build();

        return ordersCancleRepository.save(orderCancle);
    }

    public OrdersCancle findByUserIdAndOrdersIdEquals(Long userId, Long ordersId) {
        return ordersCancleRepository.findByUserIdAndOrdersIdEquals(userId, ordersId);
    }

}
