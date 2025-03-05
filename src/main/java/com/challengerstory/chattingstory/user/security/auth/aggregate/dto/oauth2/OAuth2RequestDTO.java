package com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OAuth2RequestDTO {

    private String code;
    private String state;

}
