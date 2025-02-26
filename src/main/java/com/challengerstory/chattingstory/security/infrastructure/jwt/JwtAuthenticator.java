package com.challengerstory.chattingstory.security.infrastructure.jwt;

import com.challengerstory.chattingstory.common.exception.CommonException;
import com.challengerstory.chattingstory.common.exception.ErrorCode;
import com.challengerstory.chattingstory.security.application.service.AuthUserService;
import com.challengerstory.chattingstory.security.application.service.TokenService;
import com.challengerstory.chattingstory.security.domain.aggregate.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticator {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenService tokenService;
    private final JwtExtractor jwtExtractor;
    private final JwtUtil jwtUtil;
    private final JwtProvider jwtProvider;
    private final AuthUserService authUserService;

    public Authentication processAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = jwtExtractor.extractAccessToken(request);
        try {
            Claims claims = jwtUtil.parseClaims(accessToken);
            String email = claims.getSubject();
            return getAuthentication(email);
        } catch (ExpiredJwtException e) {
            return authenticateWithRefreshToken(request, response);
        }
    }

    private Authentication authenticateWithRefreshToken(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = jwtExtractor.extractRefreshToken(request);
        try{
            Claims claims = jwtUtil.parseClaims(refreshToken);
            String email = claims.getSubject();
            tokenService.validateRefreshToken(email, refreshToken);
            String newAccessToken = jwtProvider.refreshAccessToken(claims);
            response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX+newAccessToken);
            getAuthentication(email);
        }catch(ExpiredJwtException e){
            throw new CommonException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        return null;
    }

    private Authentication getAuthentication(String email){
        CustomUser user = authUserService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }
}
