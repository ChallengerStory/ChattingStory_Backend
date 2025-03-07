package com.challengerstory.chattingstory.user.security.auth.application.service.oauth2;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2ResponseDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;
import com.challengerstory.chattingstory.user.security.auth.application.service.AuthUserService;
import com.challengerstory.chattingstory.user.security.auth.application.service.TokenService;
import com.challengerstory.chattingstory.user.security.auth.infrastructure.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleOAuth2ServiceImpl implements GoogleOAuth2Service {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;
    private final AuthUserService authUserService;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    @Override
    public OAuth2ResponseDTO processGoogleUser(String code) {
        String googleAccessToken = getGoogleAccessToken(code);
        Map<String, String> userInfo = getGoogleUserInfo(googleAccessToken);

        log.debug("userInfo: {}", userInfo);
        CustomUser foundUser;
        try {
            foundUser = authUserService.loadUserByUsername(UserType.GOOGLE.name()+"_"+userInfo.get("id"));

        }catch (UsernameNotFoundException e){
            foundUser = authUserService.registOAuth2User(UserType.GOOGLE, userInfo.get("id"));
        }
        String accessToken = jwtProvider.generateAccessToken(foundUser);
        String refreshToken = jwtProvider.generateRefreshToken(foundUser);
        tokenService.saveRefreshToken(foundUser.getUsername(), refreshToken);
        OAuth2ResponseDTO res = new OAuth2ResponseDTO();
        res.setUserIdentifier(foundUser.getUserIdentifier());
        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);

        return res;
    }

    private String getGoogleAccessToken(String code) {
        String googleAuthUrl = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri",redirectUri);
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, String>> googleATRequest = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    googleAuthUrl,
                    HttpMethod.POST,
                    googleATRequest,
                    Map.class
            );
            Map<String, Object> responseBody = response.getBody();
            assert responseBody != null;
            return (String) responseBody.get("access_token");

        } catch (HttpClientErrorException e) {
            log.debug("e: {}", e);
            throw e;
        }
    }
    private Map<String, String> getGoogleUserInfo(String accessToken) {
        String googleUserInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
        // Alternative URL: "https://www.googleapis.com/userinfo/v2/me"

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // Set Accept header for JSON

        HttpEntity<String> googleUserInfoRequest = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    googleUserInfoUrl,
                    HttpMethod.GET,
                    googleUserInfoRequest,
                    Map.class
            );
            Map<String, Object> responseBody = response.getBody();
            log.debug("responseBody: {}", responseBody);
            Map<String, String> userInfo = new HashMap<>();

            userInfo.put("id", (String) responseBody.get("id"));
            userInfo.put("username", (String) responseBody.get("name"));
            return userInfo;
        } catch (HttpClientErrorException e) {
            log.debug("e: {}", e);
            throw e;
        }
    }
}
