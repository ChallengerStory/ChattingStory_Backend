package com.challengerstory.chattingstory.user.command.domain.repository;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserIdentifier(String userIdentifier);
}
