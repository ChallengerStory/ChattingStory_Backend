package com.challengerstory.chattingstory.user.command.application.service;

import com.challengerstory.chattingstory.user.command.aggregate.dto.normal.NormalLoginRequestDTO;
import com.challengerstory.chattingstory.user.command.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.command.domain.aggregate.userdetails.CustomUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserService extends UserDetailsService {
    @Override
    CustomUser loadUserByUsername(String username) throws UsernameNotFoundException;

    Boolean isEmailAvailable(String email);

    NormalLoginRequestDTO registNewUser(NewUserRequest newUser);
}
