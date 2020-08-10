package com.fp.cloud.configuration.oauth;

import lombok.Data;

@Data
public class Login {
    private String username;
    private String password;
    private String deviceTimestamp;
}
