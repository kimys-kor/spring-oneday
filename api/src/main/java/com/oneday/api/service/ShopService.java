package com.oneday.api.service;

import com.oneday.api.model.Shop;
import com.oneday.api.model.dto.ShopReadDto;
import com.oneday.api.model.dto.ShopRegisterDto;
import com.oneday.api.repository.ShopCustomRepository;
import com.oneday.api.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ShopCustomRepository shopCustomRepository;

    public Shop save(ShopRegisterDto shopRegisterDto) {
        Shop shop = Shop.builder()
                .email(shopRegisterDto.getEmail())
                .ownerName(shopRegisterDto.getOwnerName())
                .phoneNum(shopRegisterDto.getPhoneNum())
                .build();
        return shopRepository.save(shop);
    }


    public Page<ShopReadDto> findAll(Pageable pageable) {
        return shopCustomRepository.findAll(pageable);
    }
}
