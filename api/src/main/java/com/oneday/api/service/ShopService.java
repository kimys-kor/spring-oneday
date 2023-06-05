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
                .name(shopRegisterDto.getName())
                .email(shopRegisterDto.getEmail())
                .ownerName(shopRegisterDto.getOwnerName())
                .phoneNum(shopRegisterDto.getPhoneNum())
                .lat(shopRegisterDto.getLat())
                .lon(shopRegisterDto.getLon())
                .si(shopRegisterDto.getSi())
                .gu(shopRegisterDto.getGu())
                .dong(shopRegisterDto.getDong())
                .restAddress(shopRegisterDto.getRestAddress())
                .build();
        return shopRepository.save(shop);
    }


    public Page<ShopReadDto> findAll(Pageable pageable) {
        return shopCustomRepository.findAll(pageable);
    }

    public Shop update(Long shopId, ShopRegisterDto shopRegisterDto) {
        Shop byId = findById(shopId);
        byId.setName(shopRegisterDto.getName());
        byId.setEmail(shopRegisterDto.getEmail());
        byId.setOwnerName(shopRegisterDto.getOwnerName());
        byId.setPhoneNum(shopRegisterDto.getPhoneNum());
        byId.setLat(shopRegisterDto.getLat());
        byId.setLon(shopRegisterDto.getLon());
        byId.setSi(shopRegisterDto.getSi());
        byId.setGu(shopRegisterDto.getGu());
        byId.setDong(shopRegisterDto.getDong());
        byId.setRestAddress(shopRegisterDto.getRestAddress());
        return shopRepository.save(byId);
    }

    public Shop findById(Long shopId) {
        return shopRepository.findById(shopId).orElse(null);
    }

    public void delete(Long shopId) {
        Shop byId = findById(shopId);
        shopRepository.delete(byId);

    }
}
