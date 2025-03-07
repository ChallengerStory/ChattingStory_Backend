package com.challengerstory.chattingstory.user.security.response;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2ResponseDTO;
import com.challengerstory.chattingstory.user.security.config.CustomSecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
@RequiredArgsConstructor
@Component
public class SecurityResponseHandler {
    private final CustomSecurityProperties securityProperties;
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseDTO<?> logoutResponse = ResponseDTO.ok("logout 완료");
        sendJsonResponse(response, logoutResponse);
    }

    private void sendJsonResponse(HttpServletResponse response, Object body) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}