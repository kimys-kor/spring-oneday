package com.oneday.api.service;

import com.oneday.api.model.OrderStatus;
import com.oneday.api.model.Orders;
import com.oneday.api.model.dto.OrdersReadDto;
import com.oneday.api.model.dto.OrdersSaveDto;
import com.oneday.api.repository.OrdersCustomRepository;
import com.oneday.api.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrdersService {
    
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrdersCustomRepository ordersCustomRepository;

    public Orders save(Long memberId, OrdersSaveDto ordersSaveDto) {
        Orders orders = Orders.builder()
                .status(ordersSaveDto.getStatus())
                .shopId(ordersSaveDto.getShopId())
                .memberId(memberId)
                .address(ordersSaveDto.getAddress())
                .zipcode(ordersSaveDto.getZipcode())
                .price(ordersSaveDto.getPrice())
                .shipPrice(ordersSaveDto.getShipPrice())
                .build();
        return ordersRepository.save(orders);
    }

    public Orders saveOrders(Orders orders) {
        return ordersRepository.save(orders);
    }

    public Page<OrdersReadDto> findAll(String startDt, String endDt, OrderStatus orderStatus, Pageable pageable) {
        return ordersCustomRepository.findAll(startDt,endDt,orderStatus,pageable);
    }

    public Orders findById(Long ordersId) {
        return ordersRepository.findById(ordersId).orElse(null);
    }
}
