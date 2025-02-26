package com.challengerstory.chattingstory.user.command.application.service;
import com.challengerstory.chattingstory.common.exception.CommonException;
import com.challengerstory.chattingstory.common.exception.ErrorCode;
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

}
