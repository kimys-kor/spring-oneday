package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String password;

    private String phoneNum;
    private String email;
    private String nickname;

    private int point;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;
}
