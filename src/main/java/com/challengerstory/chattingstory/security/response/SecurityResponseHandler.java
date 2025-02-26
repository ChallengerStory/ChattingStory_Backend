package com.challengerstory.chattingstory.security.response;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.security.domain.aggregate.CustomUser;
import com.challengerstory.chattingstory.security.application.dto.NormalLoginResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class SecurityResponseHandler {

    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
                               Authentication authentication) throws IOException {
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        NormalLoginResponseDTO loginResponse = new NormalLoginResponseDTO(LocalDateTime.now(), userDetails.getUserId(), userDetails.getUsername(), userDetails.getUserType(), userDetails.getUserLogin());

        ResponseDTO<?> successResponse = ResponseDTO.ok(loginResponse);
        sendJsonResponse(response, successResponse);
    }

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