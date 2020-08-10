package com.fp.cloud.configuration.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Data
@Configuration
@ConfigurationProperties("oauth2")
@Order(0)
public class Oauth2Properties {
    private String tokenUrl;
    private String checkTokenUrl;
    private String syncClientUrl;
    private String authorizeTokenUrl;
    private String clientId;
    private String clientSecret;
    private int tokenExpired;
    private int refreshToken;

    public String getCredentials() {
        return this.getClientId() + ":" + this.getClientSecret();
    }
}
