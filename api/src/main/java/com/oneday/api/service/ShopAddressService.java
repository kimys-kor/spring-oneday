package com.oneday.api.service;

import com.oneday.api.model.ShopAddress;
import com.oneday.api.model.dto.ShopAddressDto;
import com.oneday.api.repository.ShopAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShopAddressService {

    private final ShopAddressRepository shopAddressRepository;

    public ShopAddress findById(Long shopAddressId) {
        return shopAddressRepository.findById(shopAddressId).orElse(null);
    }

    public List<Map<String, Object>> findByShopIdEquals(Long shopId) {
        return shopAddressRepository.findByShopIdEquals(shopId);
    }

    public ShopAddress save(ShopAddress shopAddress) {
        return shopAddressRepository.save(shopAddress);
    }

    public ShopAddress update(Long shopId, ShopAddressDto dto) {
        ShopAddress shopAddress = findById(shopId);
        shopAddress.setZonecode(dto.getZonecode());
        shopAddress.setAddress(dto.getAddress());
        shopAddress.setAddressType(dto.getAddressType());
        shopAddress.setLat(dto.getLat());
        shopAddress.setLon(dto.getLon());
        return shopAddressRepository.save(shopAddress);
    }

}
