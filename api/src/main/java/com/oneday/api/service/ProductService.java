package com.oneday.api.service;

import com.oneday.api.model.Product;
import com.oneday.api.model.dto.ProductRegisterDto;
import com.oneday.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product save(ProductRegisterDto productRegisterDto) {
        Product product = Product.builder()
                .shopId(productRegisterDto.getShopId())
                .productCategory(productRegisterDto.getProductCategory())
                .price(productRegisterDto.getPrice())
                .stock(productRegisterDto.getStock())
                .build();
        return productRepository.save(product);
    }

    public Product update(Long productId, ProductRegisterDto productRegisterDto) {
        Product byId = findById(productId);
        byId.setShopId(productRegisterDto.getShopId());
        byId.setProductCategory(productRegisterDto.getProductCategory());
        byId.setPrice(productRegisterDto.getPrice());
        byId.setStock(productRegisterDto.getStock());

        return productRepository.save(byId);
    }

    public List<Product> findByShopIdEquals(Long shopId) {
        return productRepository.findByShopIdEquals(shopId);
    }
}
