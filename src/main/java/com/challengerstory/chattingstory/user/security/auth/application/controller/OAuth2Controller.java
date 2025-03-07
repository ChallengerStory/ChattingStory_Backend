package com.challengerstory.chattingstory.user.security.auth.application.controller;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2RequestDTO;
import com.challengerstory.chattingstory.user.security.auth.application.service.GoogleOAuth2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oauth2")
public class OAuth2Controller {

    private final GoogleOAuth2Service googleOAuth2Service;


    @PostMapping("/google")
    public ResponseDTO<?> processGoogleOAuth2User(@RequestBody OAuth2RequestDTO oauth2RequestDTO) {
        log.debug("oauth2RequestDTO: {}", oauth2RequestDTO);
        return ResponseDTO.ok(googleOAuth2Service.processGoogleOAuth2User(oauth2RequestDTO));

    }

}
