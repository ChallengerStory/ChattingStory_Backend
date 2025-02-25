package com.challengerstory.chattingstory.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthResponseDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("time_stamp")
    private final LocalDateTime timestamp;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("user_login")
    private String userLogin;

    @JsonProperty("user_type")
    private String userType;

}
