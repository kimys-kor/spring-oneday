package com.oneday.api.service;

import com.oneday.api.model.UserBasketProductOption;
import com.oneday.api.repository.UserBasketProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserBasketProductOptionService {

    private final UserBasketProductOptionRepository userBasketProductOptionRepository;

    public UserBasketProductOption save(Long productOptionId, Long userBasketProductId) {
        UserBasketProductOption userBasketProductOption = UserBasketProductOption.builder()
                .productOptionId(productOptionId)
                .userBasketProductId(userBasketProductId)
                .build();

        return userBasketProductOptionRepository.save(userBasketProductOption);
    }

    public List<Map<String,Object>> findAllByUserBasketMenuId(Long userBasketProductId) {
        return userBasketProductOptionRepository.findAllByUserBasketProductId(userBasketProductId);
    }

    public void deleteAll(Long userBasketMenuId) {
        List<UserBasketProductOption> basketOptionList = userBasketProductOptionRepository.findAllByUserBasketProductIdEquals(userBasketMenuId);
        userBasketProductOptionRepository.deleteAll(basketOptionList);
    }
}
