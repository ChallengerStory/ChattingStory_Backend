package com.challengerstory.chattingstory.user.command.application.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException;
}
