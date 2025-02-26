package com.challengerstory.chattingstory.security.response;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.security.aggregate.CustomUser;
import com.challengerstory.chattingstory.security.aggregate.dto.NormalLoginResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
        sendJsonResponse(response, HttpStatus.OK, successResponse);
    }



    private void sendJsonResponse(HttpServletResponse response, HttpStatus status, Object body) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}