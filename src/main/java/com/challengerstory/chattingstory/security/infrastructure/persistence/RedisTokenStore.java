package com.challengerstory.chattingstory.security.infrastructure.persistence;

import com.challengerstory.chattingstory.security.config.CustomSecurityProperties;
import com.challengerstory.chattingstory.security.domain.repository.TokenStore;
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
    public void saveRefreshToken(String email, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + email;
        redisTemplate.opsForValue().set(
                key,
                refreshToken,
                customSecurityProperties.getRefreshExpirationTime(),
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public String getRefreshToken(String email) {
        String key = REFRESH_TOKEN_PREFIX + email;
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void removeRefreshToken(String email) {
        String key = REFRESH_TOKEN_PREFIX + email;
        redisTemplate.delete(key);
    }

    @Override
    public void addToBlacklist(String token, String email, long remainingTime) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(
                key,
                email,
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
