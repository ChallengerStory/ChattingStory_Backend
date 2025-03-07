package com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OAuth2RequestDTO {
    @JsonProperty("user_type")
    private UserType userType;
    private String code;
    private String state;

}
