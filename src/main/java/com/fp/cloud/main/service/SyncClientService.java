package com.fp.cloud.main.service;

import com.fp.cloud.configuration.oauth.Oauth2Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Service
public class SyncClientService {

    @Autowired
    private Oauth2Properties oauth2Properties;

    public void sync(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> req = new HttpEntity<String>(headers);
        String response = null;
        try {
            response = restTemplate.patchForObject(oauth2Properties.getTokenUrl(), req, String.class);
        } catch (HttpClientErrorException e) {
            log.info(e.getMessage());
        }
        log.info(response);
    }

}
