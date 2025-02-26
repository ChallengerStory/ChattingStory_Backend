package com.challengerstory.chattingstory.security.application.controller;

import com.challengerstory.chattingstory.common.ResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/refresh")
    public ResponseDTO<?> refreshToken(){
        return ResponseDTO.ok("AT 재발급 성공");
    }
}
