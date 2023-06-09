package com.oneday.api.model;

import com.oneday.api.model.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointHistory extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POINTHISTORY_ID")
    private Long id;

    private Long ordersId;

    private Long riderId;

    private Double point;

    public PointHistory(Long ordersId, Long riderId, Double point) {
        this.ordersId = ordersId;
        this.riderId = riderId;
        this.point = point;
    }
}
