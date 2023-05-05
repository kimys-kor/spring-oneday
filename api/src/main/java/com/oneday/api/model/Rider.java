package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rider extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RIDER_ID")
    private Long id;

    private String phone;
    private String riderName;
    private String email;

    private int point;


    // 다른 필드와 메서드들
    @OneToMany(mappedBy = "rider")
    private List<Orders> orders = new ArrayList<>();
}
