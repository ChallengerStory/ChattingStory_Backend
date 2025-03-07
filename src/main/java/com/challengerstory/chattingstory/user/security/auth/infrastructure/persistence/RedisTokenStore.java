package com.challengerstory.chattingstory.user.security.auth.infrastructure.persistence;

import com.challengerstory.chattingstory.user.security.config.CustomSecurityProperties;
import com.challengerstory.chattingstory.user.security.auth.repository.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisTokenStore implements TokenStore {
    private static final String REFRESH_TOKEN_PREFIX = "RT:";
    private static final String BLACKLIST_PREFIX = "BL:";

    private final RedisTemplate<String, String> redisTemplate;
    private final CustomSecurityProperties customSecurityProperties;

    @Override
    public void saveRefreshToken(String userIdentifier, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + userIdentifier;
        redisTemplate.opsForValue().set(
                key,
                refreshToken,
                customSecurityProperties.getRefreshExpirationTime(),
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public String getRefreshToken(String userIdentifier) {
        String key = REFRESH_TOKEN_PREFIX + userIdentifier;
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void removeRefreshToken(String userIdentifier) {
        String key = REFRESH_TOKEN_PREFIX + userIdentifier;
        redisTemplate.delete(key);
    }

    @Override
    public void addToBlacklist(String token, String userIdentifier, long remainingTime) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(
                key,
                userIdentifier,
                remainingTime,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

}
