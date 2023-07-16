package com.oneday.api.model;

import com.oneday.api.model.base.BaseTime;
import com.oneday.api.model.base.UserRole;
import com.oneday.api.model.base.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    private String phoneNum;
    private String nickname;


    @ColumnDefault("0")
    private int point;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // oauth를 통해 가입한 회원
    private String provider;  // google
    private String providerId; // googleId(primaryKey)

    private LocalDateTime lastLogin;



    public User(String email, String password, String nickname, String phoneNum) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.role = UserRole.ROLE_USER;
        this.status = UserStatus.NORMAL;
    }
}
