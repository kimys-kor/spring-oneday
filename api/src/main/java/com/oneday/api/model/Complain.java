package com.oneday.api.model;

import com.oneday.api.model.base.BaseTime;
import com.oneday.api.model.base.ComplainCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Complain extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ordersId;

    private Long userId;

    private ComplainCategory complainCategory;
}
