package com.oneday.api.service;

import com.oneday.api.model.UserAddress;
import com.oneday.api.model.dto.MemberAddressDto;
import com.oneday.api.repository.UserAddresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserAddresService {

    private final UserAddresRepository userAddressRepository;

    public UserAddress findById(Long memberAddressId) {
        return userAddressRepository.findById(memberAddressId).orElse(null);
    }

    public List<Map<String, Object>> findByUserIdEquals(Long userId) {
        return userAddressRepository.findByUserIdEquals(userId);
    }

    public UserAddress save(UserAddress userAddress) {
        return userAddressRepository.save(userAddress);
    }

    public UserAddress update(Long userId, MemberAddressDto dto) {
        UserAddress userAddress = findById(userId);
        userAddress.setZonecode(dto.getZonecode());
        userAddress.setAddress(dto.getAddress());
        userAddress.setAddressType(dto.getAddressType());
        userAddress.setLat(dto.getLat());
        userAddress.setLon(dto.getLon());
        return userAddressRepository.save(userAddress);
    }

    public Map<String, Object> deleteById(Long userId,Long userAddressId) {
        Map<String, Object> result = new HashMap();
        UserAddress byId = findById(userAddressId);

        // 같은 유저인지 확인
        if (byId.getUserId() == userId) {
            userAddressRepository.deleteById(userAddressId);
            result.put("data", "삭제 완료 되었습니다.");
        } else {
            result.put("data", "내 주소가 아닙니다.");
        }
        return result;
    }

}
