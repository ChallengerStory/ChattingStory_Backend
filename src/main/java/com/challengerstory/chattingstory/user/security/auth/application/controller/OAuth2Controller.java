package com.challengerstory.chattingstory.user.security.auth.application.controller;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2RequestDTO;
import com.challengerstory.chattingstory.user.security.auth.application.service.AuthUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oauth2")
public class OAuth2Controller {

    private final AuthUserService authUserService;


    @PostMapping("/google/access_token")
    public ResponseDTO<?> processGoogleOAuth2User(@RequestBody OAuth2RequestDTO oAuth2RequestDTO) {
        log.debug("oAuth2RequestDTO: {}", oAuth2RequestDTO);
        return null;
    }

}
