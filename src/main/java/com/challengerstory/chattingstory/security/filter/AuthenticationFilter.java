package com.challengerstory.chattingstory.security.filter;

import com.challengerstory.chattingstory.security.aggregate.dto.NormalLoginRequestDTO;
import com.challengerstory.chattingstory.security.response.SecurityResponseHandler;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
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

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                SecurityResponseHandler securityResponseHandler,
                                ObjectMapper objectMapper) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.securityResponseHandler = securityResponseHandler;
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
