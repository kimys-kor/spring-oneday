package com.oneday.api.service;

import com.oneday.api.model.OrdersProduct;
import com.oneday.api.repository.OrdersProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrdersProductService {

    private final OrdersProductRepository ordersProductRepository;

    public OrdersProduct save(Long ordersId, Long productId, int quantity) {
        OrdersProduct ordersProduct = OrdersProduct.builder()
                .ordersId(ordersId)
                .productId(productId)
                .quantity(quantity)
                .build();

        return ordersProductRepository.save(ordersProduct);
    }

    public List<Map<String,Object>> findAllByOrdersId(Long ordersId) {
        return ordersProductRepository.findAllByOrdersId(ordersId);
    }

}
