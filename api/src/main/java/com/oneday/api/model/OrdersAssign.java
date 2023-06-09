package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrdersAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_ASSIGN_ID")
    private Long id;

    private Long riderId;

    private Long ordersId;


}
