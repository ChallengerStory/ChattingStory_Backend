package com.challengerstory.chattingstory.security.aggregate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
public class NormalLoginResponseDTO {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("time_stamp")
    private final LocalDateTime timestamp;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("user_type")
    private String userType;

    @JsonProperty("user_login")
    private String userLogin;

}
