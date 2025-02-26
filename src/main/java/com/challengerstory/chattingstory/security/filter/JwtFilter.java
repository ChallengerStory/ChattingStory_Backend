package com.challengerstory.chattingstory.security.filter;

import com.challengerstory.chattingstory.common.exception.CommonException;
import com.challengerstory.chattingstory.security.config.CustomSecurityProperties;
import com.challengerstory.chattingstory.security.infrastructure.jwt.JwtAuthenticator;
import com.challengerstory.chattingstory.security.response.CustomSecurityExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtAuthenticator jwtAuthenticator;
    private final CustomSecurityProperties customSecurityProperties;
    private final CustomSecurityExceptionHandler customSecurityExceptionHandler;
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            Authentication authentication = jwtAuthenticator.processAuthentication(request, response);
            if(authentication != null && authentication.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("authentication set in Security Context");
            }
            filterChain.doFilter(request, response);
        } catch(CommonException e){
            customSecurityExceptionHandler.handleAuthenticationException(response, e.getErrorCode());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();

        boolean shouldSkip = customSecurityProperties.getSkipPaths().stream().anyMatch(pattern -> new AntPathMatcher().match(pattern, requestURI));
        if (shouldSkip) {
            log.debug("skipping jwt filter for request: {}", requestURI);
        }
        return shouldSkip;
    }
}
