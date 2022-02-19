package com.mediacare.rest.exception;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private final MessageSource accessor;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String message=new MessageSourceAccessor(accessor).getMessage("accessdenied");
        response.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
    }
}
