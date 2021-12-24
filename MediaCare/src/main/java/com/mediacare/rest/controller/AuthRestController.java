package com.mediacare.rest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediacare.rest.dto.AuthetcationResponse;
import com.mediacare.rest.dto.LoginRequest;
import com.mediacare.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthRestController {

	private final AuthService authService;
	
	@PostMapping("/login")
	public AuthetcationResponse login(@RequestBody LoginRequest loginRequest) {
		
		return authService.login(loginRequest);
	}
}
