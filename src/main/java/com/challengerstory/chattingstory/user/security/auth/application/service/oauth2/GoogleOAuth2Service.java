package com.challengerstory.chattingstory.user.security.auth.application.service.oauth2;

import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2ResponseDTO;

public interface GoogleOAuth2Service {

    public OAuth2ResponseDTO processGoogleUser(String code);

}
