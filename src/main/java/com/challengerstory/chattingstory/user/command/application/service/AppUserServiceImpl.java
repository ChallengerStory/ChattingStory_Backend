package com.challengerstory.chattingstory.user.command.application.service;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;

}
