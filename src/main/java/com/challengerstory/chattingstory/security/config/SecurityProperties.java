package com.challengerstory.chattingstory.security.config;


import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("security")
public class SecurityProperties {
    private final long accessExpirationTime;
    private final long refreshExpirationTime;
    private final List<String> skipPaths;

    public SecurityProperties(long accessExpirationTime, long refreshExpirationTime, List<String> skipPaths) {
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
        this.skipPaths = skipPaths != null ? skipPaths : new ArrayList<>();
    }
}
