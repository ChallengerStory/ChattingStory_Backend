package com.challengerstory.chattingstory.security.token.util;

import com.challengerstory.chattingstory.security.aggregate.CustomUser;
import com.challengerstory.chattingstory.user.command.application.service.AppUserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
    private final Key secretKey;
    private final AppUserService appUserService;

    public JwtUtil(@Value("${security.secret}") String secret, AppUserService appUserService) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.appUserService = appUserService;
    }

    public String signToken(JwtBuilder builder) {
        return builder.signWith(secretKey, SignatureAlgorithm.HS512).compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
     *  설명.
     *   access token : userType + userLogin
     *   refresh token: email
     * */
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getExpiration().before(new Date());
        } catch(ExpiredJwtException e){
            return true;
        }
    }

    public Long getRemainingTime(@NotNull String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration().getTime() - new Date().getTime();
    }

    /* 설명. accessToken을 통한 인증 객체 추출 */
    public Authentication getAuthentication(String accessToken) {
        CustomUser userDetails = (CustomUser) appUserService.loadUserByUserIdentifier(getSubject(accessToken));
        Claims claims = parseClaims(accessToken);
        Collection<? extends GrantedAuthority> authorities;
        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        } else {
            /* 설명. Claim에서 권한 정보 가져오기
                인증되면 뒤 필터 동작 x
                인증 안되면 다음 필터 실행
                */
            authorities =
                    Arrays.stream(claims.get("auth").toString()
                                    .replace("[", "")
                                    .replace("]", "")
                                    .split(", "))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
        }
        log.debug("authorities: {}", authorities);
        // principal: 관리될 객체를 적어야됨

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);



    }

}
