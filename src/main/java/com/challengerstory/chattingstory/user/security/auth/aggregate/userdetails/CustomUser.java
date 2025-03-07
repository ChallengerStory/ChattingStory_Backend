package com.challengerstory.chattingstory.user.security.auth.aggregate.userdetails;

import com.challengerstory.chattingstory.user.command.domain.aggregate.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
public class CustomUser extends User {
    private Long userId;
    private String userType;
    private String userIdentifier;

    public CustomUser(UserEntity userEntity, List<GrantedAuthority> authorities){
        super(userEntity.getUserType()+"@"+userEntity.getUserIdentifier(), userEntity.getPassword(), authorities);
        this.userId = userEntity.getUserId();
        this.userType = userEntity.getUserType().name();
        this.userIdentifier = userEntity.getUserIdentifier();

    }
}
