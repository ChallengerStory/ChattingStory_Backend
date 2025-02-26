package com.challengerstory.chattingstory.security.domain.repository;

public interface TokenStore {
    void saveRefreshToken(String email, String refreshToken);
    String getRefreshToken(String email);
    void removeRefreshToken(String email);
    void addToBlacklist(String token, String email, long remainingTime);
    boolean isBlacklisted(String token);

}
