package com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OAuth2ResponseDTO {

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("user_identifier")
    String userIdentifier;

    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("refresh_token")
    String refreshToken;

    @JsonProperty("profile_url")
    String profileUrl;

}
