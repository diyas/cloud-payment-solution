package com.fp.cloud.configuration.oauth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class ResponseToken {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "access_token")
    private String access_token;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "token_type")
    private String token_type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "refresh_token")
    private String refresh_token;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "expires_in")
    private int expires_in;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "scope")
    private String scope;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "jti")
    private String jti;
}
