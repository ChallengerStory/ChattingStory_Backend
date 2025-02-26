package com.challengerstory.chattingstory.user.command.application.controller;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.user.command.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.command.application.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;

    @GetMapping("/refresh")
    public ResponseDTO<?> refreshToken(){
        return ResponseDTO.ok("AT 재발급 성공");
    }

    @PostMapping("/register")
    public ResponseDTO<?> registNewUser(@RequestBody NewUserRequest newUserRequest){
        return ResponseDTO.ok(appUserService.registNormalUser(newUserRequest));
    }
}
