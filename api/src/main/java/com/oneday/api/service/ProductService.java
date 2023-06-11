package com.oneday.api.service;

import com.oneday.api.model.Product;
import com.oneday.api.model.Shop;
import com.oneday.api.model.base.ProductCategory;
import com.oneday.api.model.dto.ProductRegisterDto;
import com.oneday.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionService productOptionService;


    public Product save(ProductRegisterDto productRegisterDto) {


        Product product = Product.builder()
                .shopId(productRegisterDto.getShopId())
                .productCategory(productRegisterDto.getProductCategory())
                .name(productRegisterDto.getName())
                .price(productRegisterDto.getPrice())
                .stock(productRegisterDto.getStock())
                .build();
        return productRepository.save(product);
    }

    public Product update(Long productId, ProductRegisterDto productRegisterDto) {

        Product byId = findById(productId);
        byId.setProductCategory(productRegisterDto.getProductCategory());
        byId.setPrice(productRegisterDto.getPrice());
        byId.setStock(productRegisterDto.getStock());

        return productRepository.save(byId);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    // shopId로 찾기
    public List<Product> findAllByShopIdEquals(Long shopId) {
        return productRepository.findAllByShopIdEquals(shopId);
    }

    // category로 찾기
    public List<Map<String, Object>> findAllByCategoryEquals(ProductCategory productCategory) {
        return productRepository.findAllByCategoryEquals(productCategory.name());
    }

    // 상품id로 삭제
    public void deleteByIdEquals(Long productId) {
        Product byId = findById(productId);
        productRepository.delete(byId);

        List<Long> idList = new ArrayList<>();
        idList.add(byId.getId());
        
        // 상품의 옵션들 삭제
        productOptionService.deleteAll(idList);

    }
    


    public void deleteAll(List<Product> productList) {
        productRepository.deleteAll(productList);
    }
}
