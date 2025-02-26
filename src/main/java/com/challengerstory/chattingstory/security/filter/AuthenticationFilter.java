package com.challengerstory.chattingstory.security.filter;

import com.challengerstory.chattingstory.security.application.dto.NormalLoginRequestDTO;
import com.challengerstory.chattingstory.security.response.CustomSecurityExceptionHandler;
import com.challengerstory.chattingstory.security.response.SecurityResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final SecurityResponseHandler securityResponseHandler;
    private final CustomSecurityExceptionHandler customSecurityExceptionHandler;
    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                ObjectMapper objectMapper, SecurityResponseHandler securityResponseHandler, CustomSecurityExceptionHandler customSecurityExceptionHandler) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.securityResponseHandler = securityResponseHandler;
        this.customSecurityExceptionHandler = customSecurityExceptionHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            NormalLoginRequestDTO loginRequest = objectMapper.readValue(
                    request.getInputStream(),
                    NormalLoginRequestDTO.class);

            log.debug("loginRequest: {}", loginRequest);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        securityResponseHandler.onLoginSuccess(request, response, authResult);
    }

}
