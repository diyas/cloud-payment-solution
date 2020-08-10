package com.fp.cloud.configuration.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TokenPayload implements Serializable {
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "revoke_token")
    private String revokeToken;
}
