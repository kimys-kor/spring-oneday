package com.oneday.api.service;

import com.oneday.api.model.*;
import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.base.UserStatus;
import com.oneday.api.model.dto.*;
import com.oneday.api.repository.RiderCustomRepository;
import com.oneday.api.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;

    private final RiderCustomRepository riderCustomRepository;

    private final PointHistoryService pointHistoryService;

    private final OrdersService ordersService;

    public Rider save(RiderRegisterDto dto) {
        Rider rider = Rider.builder()
                .email(dto.getEmail())
                .riderName(dto.getRiderName())
                .phone(dto.getPhone())
                .point(0)
                .status(UserStatus.NORMAL)
                .build();
        return riderRepository.save(rider);
    }


    public Map<String, Object> findAll(Pageable pageable) {
        Page<RiderReadDto> pageObject = riderCustomRepository.findAll(pageable);

        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("rider",pageObject.getContent());
        result.put("totalItem", pageObject.getTotalElements());

        return result;
    }



    public Rider findById(Long riderId) {
        return riderRepository.findById(riderId).orElse(null);
    }

    public Map<String,Object> findByIdForAdmin(Long riderId) {
        RiderDetailDto rider = riderCustomRepository.findOne(riderId);

        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("rider", rider);

        return result;
    }

    public void delete(Long riderId) {
        riderRepository.deleteById(riderId);

    }

    public Rider update(Long riderId, RiderRegisterDto dto) {
        Rider byId = findById(riderId);
        byId.setEmail(dto.getEmail());
        byId.setPhone(dto.getPhone());
        byId.setRiderName(dto.getRiderName());
        return riderRepository.save(byId);
    }

    public Rider ordersComplete(Long ordersId, Long riderId) {
        // 주문상태 수정
        Orders orders = ordersService.findById(ordersId);
        orders.setOrderStatus(OrderStatus.COMPLETE);
        ordersService.saveOrders(orders);

        double point = orders.getShipPrice() * 0.9;

        // 포인트 히스토리 저장
        PointHistory pointHistory = new PointHistory(ordersId, riderId, point);
        pointHistoryService.save(pointHistory);
        

        // 라이더 포인트 업데이트
        Rider byId = findById(riderId);
        byId.setPoint(point);
        return riderRepository.save(byId);
    }
}
