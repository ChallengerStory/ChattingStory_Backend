package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.OAuthLoginResponseDTO;
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
    public OAuthLoginResponseDTO loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserIdentifier(userIdentifier);
        if (user == null){
            throw new UsernameNotFoundException(userIdentifier + " not found");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        return new OAuthLoginResponseDTO(user, grantedAuthorities);
    }
}
