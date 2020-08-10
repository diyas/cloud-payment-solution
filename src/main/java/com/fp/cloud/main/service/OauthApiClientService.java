package com.fp.cloud.main.service;

import com.fp.cloud.configuration.oauth.Login;
import com.fp.cloud.configuration.oauth.Oauth2Properties;
import com.fp.cloud.configuration.oauth.ResponseToken;
import com.fp.cloud.main.domain.UserView;
import com.fp.cloud.main.global.payload.Response;
import com.fp.cloud.main.repository.UserViewRepo;
import com.fp.cloud.utility.Utility;
import lombok.extern.log4j.Log4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class OauthApiClientService {

    @Autowired
    private Oauth2Properties oauth2Properties;

    @Autowired
    private UserViewRepo userRepo;

    public ResponseEntity<Response> getToken(String authorization, Login request, String token, boolean isRefresh) {
        if (authorization == null)
            authorization = new String(Base64.encodeBase64(oauth2Properties.getCredentials().getBytes()));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(authorization);
        HttpEntity<String> req = new HttpEntity<String>(headers);
        String accessTokenUrl = "";
        if (!isRefresh) {
            accessTokenUrl = oauth2Properties.getTokenUrl();
            accessTokenUrl += "?username=" + request.getUsername();
            accessTokenUrl += "&password=" + request.getPassword();
            accessTokenUrl += "&salt_password=" + request.getDeviceTimestamp();
            accessTokenUrl += "&grant_type=password";
        } else {
            accessTokenUrl = oauth2Properties.getTokenUrl();
            accessTokenUrl += "?refresh_token=" + token;
            accessTokenUrl += "&grant_type=refresh_token";
        }
        if (!validClient(authorization, request.getUsername()) && !isRefresh)
            return Utility.setResponse("User ID not valid in Client", null);

        ResponseEntity<ResponseToken> response = null;
        try {
            response = restTemplate.postForEntity(accessTokenUrl, req, ResponseToken.class);
        } catch (HttpClientErrorException e) {
            return Utility.setResponse(e.getStatusCode(), e.getResponseBodyAsString(), null);
        }
        return Utility.setResponse("", response.getBody());
    }

    private boolean validClient(String auth, String userId){
        if (userId == null || userId.isEmpty())
            return true;
        boolean valid = true;
        String clientId = Utility.getClientId(auth);
        UserView user = userRepo.findByUsernameAndClientId(userId, clientId);
        if (user == null)
            valid = false;
        return valid;
    }
}
