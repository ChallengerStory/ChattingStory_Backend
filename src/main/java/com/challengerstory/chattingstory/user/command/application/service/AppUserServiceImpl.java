package com.challengerstory.chattingstory.user.command.application.service;
import com.challengerstory.chattingstory.user.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

}
