package com.challengerstory.chattingstory.user.command.domain.repository;

import com.challengerstory.chattingstory.user.command.aggregate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
