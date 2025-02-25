package com.challengerstory.chattingstory.user.command.application.service;
import com.challengerstory.chattingstory.security.aggregate.CustomUser;
import com.challengerstory.chattingstory.user.command.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 입력 형식 확인
        if (username.contains("_")) {
            // OAuth 사용자 로그인 (PROVIDER__ID 형식)
            return loadOAuthUser(username);
        } else {
            // 일반 사용자 로그인 (이메일 형식)
            return loadRegularUser(username);
        }
    }

    private UserDetails loadRegularUser(String email) {
        UserEntity user = userRepository.findByEmail(email);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));


        // 일반 사용자 정보로 UserDetails 생성
        return new CustomUser(
                user,
                grantedAuthorities,
                user.getUserId(),
                user.getUserType().toString(),
                user.getUserLogin()
        );
    }

    private UserDetails loadOAuthUser(String oauthIdentifier) {
        // PROVIDER__ID 형식 파싱
        String[] parts = oauthIdentifier.split("_", 2);
        UserType userType = UserType.valueOf(parts[0]); // GOOGLE, FACEBOOK 등
        String providerId = parts[1];

        UserEntity user = userRepository.findByUserLoginAndUserType(providerId, userType);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));


        // OAuth 사용자는 보통 빈 비밀번호 또는 특수 표시자 사용
        return new CustomUser(
                user,
                grantedAuthorities,
                user.getUserId(),
                user.getUserType().toString(),
                user.getUserLogin()
                );
    }
}
