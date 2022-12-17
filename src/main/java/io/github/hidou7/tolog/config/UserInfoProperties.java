package io.github.hidou7.tolog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("tolog")
public class UserInfoProperties {

    private String username;

    private String password;
}
