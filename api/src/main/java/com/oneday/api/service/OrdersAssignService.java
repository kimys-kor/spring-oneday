package com.oneday.api.service;

import com.oneday.api.model.OrdersAssign;
import com.oneday.api.repository.OrdersAssignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersAssignService {

    @Autowired
    OrdersAssignRepository ordersAssignRepository;

    public OrdersAssign save(OrdersAssign ordersAssign) {
        return ordersAssignRepository.save(ordersAssign);
    }
}
