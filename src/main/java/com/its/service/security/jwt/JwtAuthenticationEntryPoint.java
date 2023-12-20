package com.its.service.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        if (authException.getClass().isAnnotationPresent(ResponseStatus.class)) {
            // If the exception has a @ResponseStatus annotation, it is a business logic error
            throw authException;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", System.currentTimeMillis());
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message", "Authentication failed: " + authException.getMessage());

        String errorResponse = objectMapper.writeValueAsString(errorDetails);
        response.getWriter().write(errorResponse);
//        System.out.println("Entry Request: " + request.getRequestURI());
//        System.out.println("Entry Contain: " + request.getRequestURI().contains("/its/api/v1/admin/"));
//        if (request.getRequestURI().contains("/its/api/v1/admin/") == true)
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}