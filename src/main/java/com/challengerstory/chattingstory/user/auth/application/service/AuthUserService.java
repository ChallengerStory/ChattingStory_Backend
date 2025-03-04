package com.challengerstory.chattingstory.user.auth.application.service;

import com.challengerstory.chattingstory.user.auth.aggregate.dto.normal.NormalLoginRequestDTO;
import com.challengerstory.chattingstory.user.auth.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.auth.aggregate.userdetails.CustomUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserService extends UserDetailsService {
    @Override
    CustomUser loadUserByUsername(String username) throws UsernameNotFoundException;

    Boolean isEmailAvailable(String email);

    NormalLoginRequestDTO registNewUser(NewUserRequest newUser);
}
