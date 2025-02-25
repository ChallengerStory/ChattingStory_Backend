package com.challengerstory.chattingstory.user.command.domain.repository;

import com.challengerstory.chattingstory.user.command.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.aggregate.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserLoginAndUserType(String providerId, UserType userType);
}
