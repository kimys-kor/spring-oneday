package com.oneday.api.model;

import com.oneday.api.model.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Shop extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOP_ID")
    private Long id;

    private String name;

    private String ownerName;

    // 사업자번호
    private String businessNumber;

    // 연락처
    private String contactNumber;

    // 주소
    private String shopAddress;
    // 매장소개
    private String shopDescription;

    // 운영시간
    private String time;

    private BigDecimal lat;
    private BigDecimal lon;

    private String profile1;
    private String profile2;
    private String profile3;

    private int reviewNum;

    public Shop(String name, String ownerName, String businessNumber, String contactNumber,
                String shopAddress, String time, String shopDescription, BigDecimal lat, BigDecimal lon,
                String profile1, String profile2, String profile3) {
        this.name = name;
        this.ownerName = ownerName;
        this.businessNumber = businessNumber;
        this.contactNumber = contactNumber;
        this.shopAddress = shopAddress;
        this.time = time;
        this.shopDescription = shopDescription;
        this.lat = lat;
        this.lon = lon;
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.profile3 = profile3;
        this.reviewNum = 0;
    }
}
