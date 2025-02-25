package com.challengerstory.chattingstory.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
