package com.oneday.api.model;

import com.oneday.api.model.base.BaseTime;
import com.oneday.api.model.base.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rider extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RIDER_ID")
    private Long id;

    private String phone;
    private String riderName;
    private String email;

    private double point;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


}
