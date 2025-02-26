package com.challengerstory.chattingstory.user.security.filter;

import com.challengerstory.chattingstory.user.command.application.service.TokenService;
import com.challengerstory.chattingstory.user.command.infrastructure.jwt.JwtExtractor;
import com.challengerstory.chattingstory.user.command.infrastructure.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutFilter implements LogoutHandler {

    private final JwtExtractor jwtExtractor;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String refreshToken = jwtExtractor.extractRefreshToken(request);
        String accessToken = jwtExtractor.extractAccessToken(request);
        String email = jwtUtil.getSubject(refreshToken);

        tokenService.removeRefreshToken(email);
        tokenService.addToBlacklist(accessToken);


    }
}
