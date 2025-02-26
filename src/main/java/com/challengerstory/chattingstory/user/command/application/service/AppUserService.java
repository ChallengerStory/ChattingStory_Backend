package com.challengerstory.chattingstory.user.command.application.service;


import com.challengerstory.chattingstory.user.command.aggregate.dto.normal.NormalLoginResponseDTO;
import com.challengerstory.chattingstory.user.command.aggregate.vo.NewUserRequest;

public interface AppUserService  {

    NormalLoginResponseDTO registNormalUser(NewUserRequest newUserRequest);
}
