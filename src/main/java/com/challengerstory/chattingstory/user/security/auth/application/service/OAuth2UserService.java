package com.challengerstory.chattingstory.user.security.auth.application.service;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserType;
import com.challengerstory.chattingstory.user.security.auth.aggregate.dto.oauth2.OAuth2RequestDTO;
import com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails.CustomUser;


public interface OAuth2UserService{
    CustomUser processOAuth2User(OAuth2RequestDTO oAuth2RequestDTO);
    CustomUser registOAuth2User(UserType userType, String id);
}
