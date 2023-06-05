package com.oneday.api.service;

import com.oneday.api.model.Orders;
import com.oneday.api.model.dto.OrdersSaveDto;
import com.oneday.api.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

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
}
