package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService{
    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
