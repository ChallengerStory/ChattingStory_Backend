package com.challengerstory.chattingstory.user.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "TBL_USER")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name="user_name", nullable = false)
    private String username;

    @Column(name = "user_identifier", nullable = false)
    private String userIdentifier;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name="user_role")
    private UserRole userRole;

    @Column(name = "password")
    private String password;

    @PrePersist
    @PreUpdate
    private void generateUserIdentifier() {
        // 구현에 따라 다른 생성 방식 사용
        this.userIdentifier = userType.name() + "_" + username;
    }



}
