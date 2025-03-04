package com.challengerstory.chattingstory.user.security.auth.application.controller;

import com.challengerstory.chattingstory.user.security.auth.application.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/oauth2")
public class OAuth2Controller {

    private final AuthUserService authUserService;


}
