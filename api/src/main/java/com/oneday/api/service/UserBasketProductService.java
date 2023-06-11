package com.oneday.api.service;

import com.oneday.api.model.UserBasketProduct;
import com.oneday.api.model.dto.BasketDto;
import com.oneday.api.repository.UserBasketProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserBasketProductService {

    private final UserBasketProductRepository userBasketProductRepository;
    private final UserBasketProductOptionService userBasketProductOptionService;
    private final ProductService productService;

    public UserBasketProduct save(BasketDto basketDto, Long userId, Long shopId) {
        UserBasketProduct userBasketProduct = UserBasketProduct.builder()
                .userId(userId)
                .productId(basketDto.getProductId())
                .quantity(basketDto.getQuantity())
                .shopId(shopId)
                .build();

        UserBasketProduct save = userBasketProductRepository.save(userBasketProduct);

        // 옵션들 저장
        for (Long productOptionId : basketDto.getProductOptionId()) {
            userBasketProductOptionService.save(productOptionId, save.getId());
        }
        return save;
    }


    public List<Map<String,Object>> findAllByUserIdEquals(Long userId) {
        List<Map<String, Object>> allByUserIdEquals = userBasketProductRepository.findAllByUserIdEquals(userId);
        List<Map<String,Object>> result = new ArrayList<>();

        for (Map<String, Object> BasketProduct : allByUserIdEquals) {
            Map<String,Object> modifiedProduct = new HashMap<>(BasketProduct);
            List<Map<String,Object>> productOption = userBasketProductOptionService.findAllByUserBasketProductId((Long) BasketProduct.get("id"));
            modifiedProduct.put("option", productOption);
            result.add(modifiedProduct);
        }
        return result;
    }

    public String deleteByUserBasketProductId(Long userId, Long userBasketProductId) {
        UserBasketProduct byId = userBasketProductRepository.findById(userBasketProductId).orElse(null);

        // 장바구니 유저 아닐시 에러 처리
        if(byId.getUserId() != userId) return "권한없음";
        userBasketProductRepository.delete(byId);
        userBasketProductOptionService.deleteAll(byId.getId());
        return "성공";
    }
}
