package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserRole;
import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.command.domain.repository.UserRepository;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2ResponseDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;
import com.challengerstory.chattingstory.user.security.auth.infrastructure.jwt.JwtExtractor;
import com.challengerstory.chattingstory.user.security.auth.infrastructure.jwt.JwtProvider;
import com.challengerstory.chattingstory.user.security.auth.infrastructure.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtExtractor jwtExtractor;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;
    private final JwtProvider tokenProvider;
    @Override
    public CustomUser loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserIdentifier(userIdentifier);
        if (user == null){
            throw new UsernameNotFoundException(userIdentifier + " not found");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        return new CustomUser(user, grantedAuthorities);
    }

    @Override
    public CustomUser registOAuth2User(UserType userType, String id, String profileUrl) {
        UserEntity user = new UserEntity();
        user.setUserType(userType);
        user.setUsername(id);
        user.setPassword(bCryptPasswordEncoder.encode(id));
        user.setUserRole(UserRole.ROLE_MEMBER);
        user.setProfileUrl(profileUrl);
        user = userRepository.save(user);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));
        return new CustomUser(user, authorities);
    }

    @Override
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtExtractor.extractRefreshToken(request);
        String username = jwtUtil.getSubject(refreshToken);
        tokenService.validateRefreshToken(username, refreshToken);

        CustomUser user = loadUserByUsername(username);
        String accessToken = tokenProvider.generateAccessToken(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);

        OAuth2ResponseDTO responseDTO = new OAuth2ResponseDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setProfileUrl(user.getProfileUrl());
        responseDTO.setUserIdentifier(user.getUserIdentifier());
        return new ResponseEntity<>(responseDTO, headers, HttpStatus.OK);
    }
}
