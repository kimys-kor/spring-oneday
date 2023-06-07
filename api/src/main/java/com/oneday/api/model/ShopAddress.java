package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShopAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOP_ADDRESS_ID")
    private Long id;

    private Long shopId;

    // 우편번호
    private String zonecode;
    // 기본 주소
    private String address;

    // 주소 타입 R 도로명, J 지번
    private String addressType;

    private String roadAddress;

    private String jibunAddress;

    private BigDecimal lat;

    private BigDecimal lon;

}
