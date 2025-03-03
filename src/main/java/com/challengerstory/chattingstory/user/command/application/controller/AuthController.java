package com.challengerstory.chattingstory.user.command.application.controller;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.user.command.aggregate.vo.NewUserRequest;
import com.challengerstory.chattingstory.user.command.application.service.AuthUserService;
import com.challengerstory.chattingstory.user.command.application.service.EmailVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUserService authUserService;
    private final EmailVerificationService emailVerificationService;
    @GetMapping("/refresh")
    public ResponseDTO<?> refreshToken(){
        return ResponseDTO.ok("AT 재발급 성공");
    }

    @GetMapping("/check-email")
    public ResponseDTO<?> checkEmail(@RequestParam String email){
        log.debug("email: {}", email);
        return ResponseDTO.ok(authUserService.isEmailAvailable(email));
    }

    @GetMapping("/send-verification")
    public ResponseDTO<?> sendVerificationCode(@RequestParam String email){
        log.debug("email: {}", email);
        emailVerificationService.sendVerificationCode(email);
        return ResponseDTO.ok(email+"이메일 전송 완료");
    }

    @GetMapping("/verify-code")
    public ResponseDTO<?> verifyCode(@RequestParam String email, @RequestParam String code){
        log.debug("email: {}", email);
        log.debug("code: {}", code);

        return ResponseDTO.ok(emailVerificationService.verifyCode(email, code));
    }

    @PostMapping("/register")
    public ResponseDTO<?> registNewUser(@RequestBody NewUserRequest newUser){
        return ResponseDTO.ok(authUserService.registNewUser(newUser));
    }


}
