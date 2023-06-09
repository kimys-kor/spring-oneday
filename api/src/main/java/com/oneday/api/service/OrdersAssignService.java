package com.oneday.api.service;

import com.oneday.api.model.OrdersAssign;
import com.oneday.api.repository.OrdersAssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersAssignService {


    private final OrdersAssignRepository ordersAssignRepository;

    public OrdersAssign save(OrdersAssign ordersAssign) {
        return ordersAssignRepository.save(ordersAssign);
    }
}
