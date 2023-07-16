package com.oneday.api.service;

import com.oneday.api.model.ProductOption;
import com.oneday.api.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;

    public ProductOption save(Long productId, String name, int price) {
        ProductOption productOption = ProductOption.builder()
                .productId(productId)
                .name(name)
                .price(price)
                .build();

        return productOptionRepository.save(productOption);
    }

    public List<Map<String, Object>> findByProductIdEquals(Long productId) {
        return productOptionRepository.findByProductIdEquals(productId);
    }

    // id 값만 찾기
    public List<Long> findIdListByProductIdEquals(Long productId) {
        return productOptionRepository.findIdListByProductIdEquals(productId);
    }

    public Optional<ProductOption> findById(Long productOptionId) {
       return productOptionRepository.findById(productOptionId);
    }

    public void delete(Long productOptionId) {
        ProductOption byId = findById(productOptionId).orElse(null);
        productOptionRepository.delete(byId);
    }

    public ProductOption update(Long productOptionId, String name, int price) {
        ProductOption byId = findById(productOptionId).orElse(null);
        byId.setProductId(productOptionId);
        byId.setName(name);
        byId.setPrice(price);
        return productOptionRepository.save(byId);
    }

    // productId 리스트로 해당 상품옵션들 삭제
    public void deleteAll(List<Long> productIdList) {
        List<ProductOption> productOptionsToDelete = productOptionRepository.findByProductIdIn(productIdList);
        productOptionRepository.deleteAll(productOptionsToDelete);
    }
}
