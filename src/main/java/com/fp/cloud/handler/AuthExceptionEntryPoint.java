package com.fp.cloud.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fp.cloud.main.global.payload.Response;
import com.fp.cloud.utility.Utility;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException {

        Map map = Utility.toMap(authException.getCause());
        Response resp = new Response();
        resp.setCode(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        resp.setMessage(map.get("error").toString());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), resp);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
