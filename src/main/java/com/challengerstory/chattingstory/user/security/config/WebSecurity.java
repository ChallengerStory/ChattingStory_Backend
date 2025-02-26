package com.challengerstory.chattingstory.user.security.config;

import com.challengerstory.chattingstory.user.command.application.service.AuthUserService;
import com.challengerstory.chattingstory.user.command.application.service.TokenService;
import com.challengerstory.chattingstory.user.security.filter.AuthenticationFilter;
import com.challengerstory.chattingstory.user.security.filter.CustomLogoutFilter;
import com.challengerstory.chattingstory.user.security.filter.JwtFilter;
import com.challengerstory.chattingstory.user.command.infrastructure.jwt.JwtAuthenticator;
import com.challengerstory.chattingstory.user.command.infrastructure.jwt.JwtExtractor;
import com.challengerstory.chattingstory.user.command.infrastructure.jwt.JwtUtil;
import com.challengerstory.chattingstory.user.security.response.CustomSecurityExceptionHandler;
import com.challengerstory.chattingstory.user.security.response.SecurityResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(CustomSecurityProperties.class)
public class WebSecurity {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthUserService authUserService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticator jwtAuthenticator;
    private final CustomSecurityProperties customSecurityProperties;
    private final SecurityResponseHandler securityResponseHandler;
    private final CustomSecurityExceptionHandler customSecurityExceptionHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtExtractor tokenExtractor) throws Exception {
        // CORS configuration and CSRF disable
        http.cors(cors -> cors.configure(http))
                .csrf(AbstractHttpConfigurer::disable);

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authUserService)
                .passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        List<AntPathRequestMatcher> skipPathMatchers = customSecurityProperties.getSkipPaths().stream()
                .map(AntPathRequestMatcher::new)
                .toList();

        http.authorizeHttpRequests(auth -> {
                    skipPathMatchers.forEach(matcher -> auth.requestMatchers(matcher).permitAll());
                    auth.anyRequest().authenticated();
                })
                .authenticationManager(authenticationManager)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(new CustomLogoutFilter(tokenExtractor, tokenService, jwtUtil))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            securityResponseHandler.onLogoutSuccess(request, response);
                        })
                )
                .addFilter(getAuthenticationFilter(authenticationManager))
                .addFilterBefore(getJwtFilter(jwtAuthenticator, customSecurityProperties),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private JwtFilter getJwtFilter(JwtAuthenticator tokenAuthenticator, CustomSecurityProperties securityProperties) {
        return new JwtFilter(
                tokenAuthenticator,
                securityProperties,
                customSecurityExceptionHandler
        );
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(
                authenticationManager,
                new ObjectMapper(),
                securityResponseHandler,
                customSecurityExceptionHandler
        );

        // 로그인 경로 설정
        authenticationFilter.setFilterProcessesUrl("/api/auth/login");
        return authenticationFilter;
    }
}