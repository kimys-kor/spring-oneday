package com.oneday.api.service;

import com.oneday.api.model.*;
import com.oneday.api.model.base.UserStatus;
import com.oneday.api.model.base.OrderStatus;
import com.oneday.api.model.dto.RiderReadDto;
import com.oneday.api.model.dto.RiderRegisterDto;
import com.oneday.api.repository.RiderCustomRepository;
import com.oneday.api.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public Page<RiderReadDto> findAll(Pageable pageable) {
        return riderCustomRepository.findAll(pageable);
    }

    public Rider findById(Long riderId) {
        return riderRepository.findById(riderId).orElse(null);
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
