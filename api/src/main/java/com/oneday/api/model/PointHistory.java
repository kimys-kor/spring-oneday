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

    @ManyToOne
    @JoinColumn(name = "RIDER_ID")
    private Rider rider;

    private Integer point;

}
