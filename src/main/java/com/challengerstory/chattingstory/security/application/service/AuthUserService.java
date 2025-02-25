package com.challengerstory.chattingstory.security.application.service;

import com.challengerstory.chattingstory.security.aggregate.CustomUser;
import com.challengerstory.chattingstory.user.command.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.application.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public interface AuthUserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
