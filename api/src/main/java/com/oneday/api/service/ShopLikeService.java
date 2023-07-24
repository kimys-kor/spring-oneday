package com.oneday.api.service;

import com.oneday.api.model.ShopLike;
import com.oneday.api.repository.ShopLikeRpository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopLikeService {

    private final ShopLikeRpository shopLikeRepository;


    public void save(Long shopId, Long userId) {
        ShopLike findBy = findByShopIdAndUserIdEquals(shopId, userId);

        if (findBy == null) {
            ShopLike shopLike = ShopLike.builder()
                    .shopId(shopId)
                    .userId(userId)
                    .build();
            shopLikeRepository.save(shopLike);
        } else {
            deleteByShopIdAndUserIdEquals(shopId,userId);
        }
    }

    public ShopLike findByShopIdAndUserIdEquals(Long shopId, Long userId) {
        return shopLikeRepository.findByShopIdAndUserIdEquals(shopId, userId);
    }

    public void deleteByShopIdAndUserIdEquals(Long shopId, Long userId) {
        shopLikeRepository.deleteByShopIdAndUserIdEquals(shopId, userId);

    }
}
