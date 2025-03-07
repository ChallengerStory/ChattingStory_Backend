package com.challengerstory.chattingstory.user.security.auth.repository;

public interface TokenStore {
    void saveRefreshToken(String userIdentifier, String refreshToken);
    String getRefreshToken(String userIdentifier);
    void removeRefreshToken(String userIdentifier);
    void addToBlacklist(String token, String userIdentifier, long remainingTime);
    boolean isBlacklisted(String token);

}
