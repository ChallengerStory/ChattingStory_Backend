package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserRole;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        return new CustomUser(user, authorities);
    }

    @Override
    public CustomUser registOAuth2User(UserType userType, String id) {
        UserEntity userToCreate = new UserEntity();
        userToCreate.setUserType(userType);
        userToCreate.setUsername(id);
        userToCreate.setPassword(bCryptPasswordEncoder.encode(id));
        userToCreate.setUserRole(UserRole.ROLE_MEMBER);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userToCreate.getUserRole().name()));
        UserEntity user = userRepository.save(userToCreate);
        return new CustomUser(user, authorities);
    }
}
