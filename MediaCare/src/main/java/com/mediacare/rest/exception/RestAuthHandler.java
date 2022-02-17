package com.mediacare.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RestAuthHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        String message="Incorrect Username Or Password";
        if (ex instanceof InsufficientAuthenticationException) {
            message="Access Denied, Please Login First!";
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
    }

}
