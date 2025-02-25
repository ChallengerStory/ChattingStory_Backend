package com.challengerstory.chattingstory.security.aggregate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NormalLoginRequestDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
