package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.OAuthLoginResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserService extends UserDetailsService {
    @Override
    OAuthLoginResponseDTO loadUserByUsername(String userIdentifier) throws UsernameNotFoundException;
}
