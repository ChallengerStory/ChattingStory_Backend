package com.challengerstory.chattingstory.user.command.application.service;
import com.challengerstory.chattingstory.user.command.aggregate.dto.normal.NormalLoginResponseDTO;
import com.challengerstory.chattingstory.user.command.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public NormalLoginResponseDTO registNormalUser(NewUserRequest newUserRequest) {
        UserEntity newUser = modelMapper.map(newUserRequest, UserEntity.class);
        log.debug("newUser: {}", newUser);
        newUser.setUserType(UserType.NORMAL);
        newUser.setLastActivatedAt(LocalDateTime.now());
        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser, NormalLoginResponseDTO.class);
    }
}
