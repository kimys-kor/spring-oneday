package com.oneday.api.service;

import com.oneday.api.model.OrdersProductOption;
import com.oneday.api.repository.OrdersProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersProductOptionService {

    private final OrdersProductOptionRepository ordersProductOptionRepository;


    public OrdersProductOption save(Long ordersId, Long ordersProductId, Long ProductOptionId) {
        OrdersProductOption ordersMenuOption = OrdersProductOption.builder()
                .orderId(ordersId)
                .ordersProductId(ordersProductId)
                .productOptionId(ProductOptionId)
                .build();
        return ordersProductOptionRepository.save(ordersMenuOption);
    }

    public List<Map<String, Object>> findAllByOrdersProductId(Long ordersProductId) {
        return ordersProductOptionRepository.findAllByOrdersProductId(ordersProductId);
    }


    public OrdersProductOption findByid(Long ordersProductOptionId) {
        return ordersProductOptionRepository.findById(ordersProductOptionId).orElse(null);
    }

}
