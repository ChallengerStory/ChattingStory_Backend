package com.challengerstory.chattingstory.security.token.util;

import com.challengerstory.chattingstory.security.response.exception.AuthErrorCode;
import com.challengerstory.chattingstory.security.response.exception.CustomAuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Slf4j
@Component
public class TokenExtractor {

    public String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("extract access Token: {}", bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7).trim();
        }
        return null;
    }

    public String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new CustomAuthException(AuthErrorCode.COOKIE_NOT_FOUND);
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) {
            log.debug("refreshToken not found");
            throw new CustomAuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        return refreshToken;
    }
}
