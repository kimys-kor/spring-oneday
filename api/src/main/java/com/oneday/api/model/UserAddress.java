package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    // 우편번호
    private String zonecode;
    // 기본 주소
    private String address;

    // 주소 타입 R 도로명, J 지번
    private String addressType;


    private BigDecimal lat;

    private BigDecimal lon;

}
