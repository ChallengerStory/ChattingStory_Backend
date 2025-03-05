package com.challengerstory.chattingstory.user.security.auth.application.service;

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
    public CustomUser loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException(email+" not found");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        return new CustomUser(user, grantedAuthorities, user.getUserId(), user.getUserType().toString(), user.getUserIdentifier());
    }

    @Override
    public Boolean isEmailAvailable(String email) {

        UserEntity user = userRepository.findByEmail(email);
        if (user == null){
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }

    @Override
    public NormalLoginRequestDTO registNewUser(NewUserRequest newUser) {
        UserEntity userToCreate = modelMapper.map(newUser, UserEntity.class);
        userToCreate.setUserType(UserType.NORMAL);
        userToCreate.setUserIdentifier(newUser.getEmail());
        return modelMapper.map(userRepository.save(userToCreate), NormalLoginRequestDTO.class);
    }

    @Override
    public CustomUser registOAuth2User(UserType userType, String id, String username) {
        UserEntity userToCreate = new UserEntity();
        userToCreate.setUserType(userType);
        userToCreate.setPassword(bCryptPasswordEncoder.encode(id));
        userToCreate.setUserIdentifier(userType.toString()+"@"+id);
        userToCreate.setLastActivatedAt(LocalDateTime.now());
        userToCreate.setEmail(userType+"_"+id+"@"+userType+".COM");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        UserEntity user = userRepository.save(userToCreate);
        return new CustomUser(user, grantedAuthorities, user.getUserId(), user.getUserType().toString(), user.getUserIdentifier());
    }
}
