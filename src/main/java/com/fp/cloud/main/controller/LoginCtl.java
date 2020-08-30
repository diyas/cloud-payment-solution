package com.fp.cloud.main.controller;

import com.fp.cloud.configuration.oauth.Login;
import com.fp.cloud.configuration.oauth.TokenPayload;
import com.fp.cloud.main.global.payload.Response;
import com.fp.cloud.main.service.OauthApiClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/auth", produces = APPLICATION_JSON_VALUE)
@Api(value = "/auth", tags = "Authorization")
public class LoginCtl {

    @Autowired
    private OauthApiClientService oauthApiClientService;

    @PostMapping("/login")
    @ApiOperation(
            value = "Generate Token", notes = "Generate New Token",
            response = Response.class)
    public ResponseEntity<Response> getToken(@RequestHeader(value = "Authorization", required = false) String authorization, @RequestBody Login request) {
        return oauthApiClientService.getToken(authorization, request, null, false);
    }

    @PostMapping("/refresh_token")
    @ApiOperation(
            value = "Refresh Token", notes = "Refresh Old Token",
            response = Response.class)
    public ResponseEntity<Response> getRefreshToken(@RequestHeader(value = "Authorization", required = false) String authorization, @RequestBody TokenPayload request) {
        return oauthApiClientService.getToken(authorization,null, request.getRefreshToken(), true);
    }
}
