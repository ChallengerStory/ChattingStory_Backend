package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2RequestDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class OAuth2UserServiceImpl implements OAuth2UserService{

    private final AuthUserService authUserService;

    @Override
    public OAuth2ResponseDTO processOAuth2User(OAuth2RequestDTO request) {

        return null;
    }
}
