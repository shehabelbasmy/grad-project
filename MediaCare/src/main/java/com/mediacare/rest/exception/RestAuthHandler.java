package com.mediacare.rest.exception;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class RestAuthHandler implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        response.setStatus(HttpStatus.OK.value());
        String json = new Gson().toJson(
    		Map.ofEntries(
				Map.entry("error", ex.getMessage()),
    			Map.entry("status", "false")
    		)
		);
        response.setContentType("application/json");
        response.getWriter().write(json);
        
    }

}
