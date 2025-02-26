package com.challengerstory.chattingstory.user.command.aggregate.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class NewUserRequest{
    @JsonProperty("email")
    String email;

    @JsonProperty("password")
    String password;

    @JsonProperty("user_login")
    String userLogin;
}
