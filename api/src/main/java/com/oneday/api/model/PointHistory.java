package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "POINTHISTORY")
@NoArgsConstructor
@AllArgsConstructor
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
