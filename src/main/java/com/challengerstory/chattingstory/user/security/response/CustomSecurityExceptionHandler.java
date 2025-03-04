package com.challengerstory.chattingstory.user.security.response;

import com.challengerstory.chattingstory.common.ResponseDTO;
import com.challengerstory.chattingstory.common.exception.CommonException;
import com.challengerstory.chattingstory.common.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSecurityExceptionHandler {

    public void handleAuthenticationException(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        ResponseDTO<Object> errorResponse = ResponseDTO.fail(new CommonException(errorCode));
        sendJsonResponse(response, errorCode.getHttpStatus(), errorResponse);
    }



    private void sendJsonResponse(HttpServletResponse response, HttpStatus status, Object body) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}