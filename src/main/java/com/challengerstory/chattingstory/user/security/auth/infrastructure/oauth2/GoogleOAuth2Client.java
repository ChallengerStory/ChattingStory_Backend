package com.challengerstory.chattingstory.user.security.auth.infrastructure.oauth2;

import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2RequestDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2ResponseDTO;

public interface GoogleOAuth2Client {
    public OAuth2ResponseDTO processOAuth2User(OAuth2RequestDTO oAuth2RequestDTO);

}
