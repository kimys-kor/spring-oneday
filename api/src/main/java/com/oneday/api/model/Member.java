package com.oneday.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    private String phoneNum;
    private String nickname;


    @ColumnDefault("0")
    private int point;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    // oauth를 통해 가입한 회원
    private String provider;  // google
    private String providerId; // googleId(primaryKey)

    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();


    public Member(String email, String password, String nickname, String phoneNum) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.role = MemberRole.ROLE_MEMBER;
        this.status = MemberStatus.NORMAL;
    }
}
