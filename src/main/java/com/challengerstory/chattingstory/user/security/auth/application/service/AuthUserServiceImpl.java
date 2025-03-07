package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserRole;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public CustomUser loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserIdentifier(userIdentifier);
        if (user == null){
            throw new UsernameNotFoundException(userIdentifier + " not found");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        return new CustomUser(user, grantedAuthorities);
    }

    @Override
    public CustomUser registOAuth2User(UserType userType, String id) {
        UserEntity user = new UserEntity();
        user.setUserType(userType);
        user.setUsername(id);
        user.setPassword(bCryptPasswordEncoder.encode(id));
        user.setUserRole(UserRole.ROLE_MEMBER);
        user = userRepository.save(user);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        return new CustomUser(user, authorities);
    }
}
