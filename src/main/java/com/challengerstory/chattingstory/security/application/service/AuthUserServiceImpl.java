package com.challengerstory.chattingstory.security.application.service;

import com.challengerstory.chattingstory.security.domain.aggregate.CustomUser;
import com.challengerstory.chattingstory.user.command.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService{
    private final UserRepository userRepository;

    @Override
    public CustomUser loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email+" not found");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        return new CustomUser(user, grantedAuthorities, user.getUserId(), user.getUserType().toString(), user.getUserLogin());
    }
}
