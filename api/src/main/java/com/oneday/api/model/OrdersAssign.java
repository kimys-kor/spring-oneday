package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ORDERSASSIGN")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrdersAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERS_ASSIGN_ID")
    private Long id;

    private Long riderId;

    private Long ordersId;


}
