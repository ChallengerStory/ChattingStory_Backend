package com.challengerstory.chattingstory.user.command.application.service;

import com.challengerstory.chattingstory.user.command.aggregate.dto.normal.NormalLoginRequestDTO;
import com.challengerstory.chattingstory.user.command.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.command.domain.aggregate.userdetails.CustomUser;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

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
        userToCreate.setUserLogin(newUser.getEmail());
        return modelMapper.map(userRepository.save(userToCreate), NormalLoginRequestDTO.class);
    }
}
