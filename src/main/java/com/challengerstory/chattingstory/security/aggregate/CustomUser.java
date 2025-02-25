package com.challengerstory.chattingstory.security.aggregate;

import com.challengerstory.chattingstory.user.command.aggregate.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class CustomUser extends User {
    private Long userId;
    private String userType;
    private String userLogin;

    public CustomUser(UserEntity userEntity, Collection<?extends GrantedAuthority> authorities, Long userId, String userType, String userLogin){
        super(userEntity.getEmail(), userEntity.getPassword(), authorities);
        this.userId = userId;
        this.userType = userType;
        this.userLogin = userLogin;

    }
}
