package com.its.service.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

//    @Override
//    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//
//        final String expired = (String) httpServletRequest.getAttribute("expired");
//        if (expired!=null){
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,expired);
//        }else{
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Login details");
//        }
//    }

    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, jakarta.servlet.ServletException {
        // Custom logic to handle authentication failures
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("payload", "You need to login first in order to perform this action.");

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}