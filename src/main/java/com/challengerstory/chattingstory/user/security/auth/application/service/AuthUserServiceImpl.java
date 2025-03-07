package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserRole;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.normal.NormalLoginRequestDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.security.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return null;
    }

    @Override
    public CustomUser registOAuth2User(UserType userType, String id, String username) {
        UserEntity userToCreate = new UserEntity();
        userToCreate.setUserType(userType);
        userToCreate.setPassword(bCryptPasswordEncoder.encode(id));
        userToCreate.setUserIdentifier(userType.toString()+"@"+id);
        userToCreate.setUserRole(UserRole.ROLE_MEMBER);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userToCreate.getUserRole().name()));
        UserEntity user = userRepository.save(userToCreate);
        return new CustomUser(user, authorities);
    }
}
