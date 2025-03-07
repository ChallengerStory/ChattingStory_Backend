package com.challengerstory.chattingstory.user.security.response;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityResponseHandler {

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