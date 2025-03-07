package com.challengerstory.chattingstory.user.security.auth.application.controller;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2RequestDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2ResponseDTO;
import com.challengerstory.chattingstory.user.security.auth.application.service.GoogleOAuth2Service;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oauth2")
public class OAuth2Controller {

    private final GoogleOAuth2Service googleOAuth2Service;


    @PostMapping("/google")
    public ResponseEntity<?> processGoogleOAuth2User(@RequestBody OAuth2RequestDTO oauth2RequestDTO, HttpServletResponse response) {
        log.debug("oauth2RequestDTO: {}", oauth2RequestDTO);
        return googleOAuth2Service.processGoogleOAuth2User(oauth2RequestDTO, response);

    }

}
