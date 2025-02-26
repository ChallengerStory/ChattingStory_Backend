package com.challengerstory.chattingstory.security.infrastructure.jwt;

import com.challengerstory.chattingstory.security.application.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtValidator {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenService tokenService;
    private final JwtExtractor jwtExtractor;
    private final JwtUtil jwtUtil;
    public Authentication processAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String accessToken = jwtExtractor.extractAccessToken(request);
        log.debug("processing authentication with at: {}", accessToken);

    }

    private boolean isValidAccessToken(String accessToken){
        if (accessToken == null || accessToken.isEmpty()){
            return false;
        }
        if (jwtUtil.isTokenExpired(accessToken)){
            return false;
        }

        return tokenService.validateAccessToken(accessToken);
    }
}
