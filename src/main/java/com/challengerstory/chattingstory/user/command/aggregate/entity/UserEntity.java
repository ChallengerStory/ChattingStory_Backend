package com.challengerstory.chattingstory.user.command.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TBL_USER")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_login", nullable = false)
    private String userLogin;

    /* 설명. OAuth까지 고려하여 email을 nullable하게 설정함 */
    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "last_activated_at")
    private LocalDateTime lastActivatedAt;

}
