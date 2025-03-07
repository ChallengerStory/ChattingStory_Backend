package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthUserService extends UserDetailsService {
    @Override
    CustomUser loadUserByUsername(String userIdentifier) throws UsernameNotFoundException;

    CustomUser registOAuth2User(UserType userType, String id, String profileUrl);

    ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
}
