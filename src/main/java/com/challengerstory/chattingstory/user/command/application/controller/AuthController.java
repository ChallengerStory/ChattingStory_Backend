package com.challengerstory.chattingstory.user.command.application.controller;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.user.command.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.command.application.service.AppUserService;
import com.challengerstory.chattingstory.user.command.application.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUserService authUserService;

    @GetMapping("/refresh")
    public ResponseDTO<?> refreshToken(){
        return ResponseDTO.ok("AT 재발급 성공");
    }

    @PostMapping("/email-check")
    public ResponseDTO<?> checkEmail(@RequestBody String email){
        return ResponseDTO.ok(authUserService.isEmailAvailable(email));
    }

    @PostMapping("/send-verification")
    public ResponseDTO<?> sendVerificationCode(@RequestBody String email){

        return ResponseDTO.ok(email+"이메일 전송 완료");
    }


}
