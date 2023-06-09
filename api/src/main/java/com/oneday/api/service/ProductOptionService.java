package com.oneday.api.service;

import com.oneday.api.model.ProductOption;
import com.oneday.api.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;

    public ProductOption save(Long productId, String name, int price) {
        ProductOption menuOption = ProductOption.builder()
                .productId(productId)
                .name(name)
                .price(price)
                .build();

        return productOptionRepository.save(menuOption);
    }

    public List<Map<String, Object>> findByMenuIdEquals(Long productId) {
        return productOptionRepository.findByProductIdEquals(productId);
    }

    public ProductOption findById(Long productOptionId) {
        return productOptionRepository.findById(productOptionId).orElse(null);
    }

    public void delete(Long productOptionId) {
        ProductOption byId = findById(productOptionId);
        productOptionRepository.delete(byId);
    }

    public ProductOption update(Long productOptionId, Long productId, String name, int price) {
        ProductOption byId = findById(productOptionId);
        byId.setProductId(productOptionId);
        byId.setName(name);
        byId.setPrice(price);
        return productOptionRepository.save(byId);
    }
}
