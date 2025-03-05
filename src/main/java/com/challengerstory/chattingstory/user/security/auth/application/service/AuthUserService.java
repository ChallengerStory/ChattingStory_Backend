package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.normal.NormalLoginRequestDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserService extends UserDetailsService {
    @Override
    CustomUser loadUserByUsername(String username) throws UsernameNotFoundException;

    Boolean isEmailAvailable(String email);

    NormalLoginRequestDTO registNewUser(NewUserRequest newUser);

    CustomUser registOAuth2User(UserType userType, String id, String username);
}
