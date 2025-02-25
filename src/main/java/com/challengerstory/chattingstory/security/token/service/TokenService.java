package com.challengerstory.chattingstory.security.token.service;


import com.challengerstory.chattingstory.security.response.exception.AuthErrorCode;
import com.challengerstory.chattingstory.security.response.exception.CustomAuthException;
import com.challengerstory.chattingstory.security.token.respository.TokenStore;
import com.challengerstory.chattingstory.security.token.util.JwtUtil;
import com.challengerstory.chattingstory.security.token.util.TokenProvider;
import org.springframework.stereotype.Service;

/**
 * Token의 Life Cycle 관련 작업 진행
 */
@Service
public class TokenService {
    private final TokenStore tokenStore;
    private final JwtUtil jwtUtil;

    public TokenService(TokenStore tokenStore, JwtUtil jwtUtil) {
        this.tokenStore = tokenStore;
        this.jwtUtil = jwtUtil;
    }

    public void addToBlacklist(String accessToken) {
        String userIdentifier = jwtUtil.getSubject(accessToken);
        Long remainingTime = jwtUtil.getRemainingTime(accessToken);
        tokenStore.addToBlacklist(accessToken, userIdentifier, remainingTime);
    }
    public void saveRefreshToken(String email, String refreshToken){
        tokenStore.saveRefreshToken(email, refreshToken);
    }
    public void validateRefreshToken(String email, String refreshToken) {
        String storedToken = tokenStore.getRefreshToken(email);
        if (storedToken == null) {
            throw new CustomAuthException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }
        if (!storedToken.equals(refreshToken)) {
            tokenStore.removeRefreshToken(email);
            throw new CustomAuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    public boolean validateAccessToken(String accessToken) {
        if (tokenStore.isBlacklisted(accessToken)) {
            throw new CustomAuthException(AuthErrorCode.ACCESS_TOKEN_BLACKLISTED);
        }
        return true;
    }

    public void removeRefreshToken(String email) {
        tokenStore.removeRefreshToken(email);
    }
}
