package com.mediacare.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediacare.dto.AuthetcationResponse;
import com.mediacare.dto.LoginRequest;
import com.mediacare.service.RestAuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthRestController {

	private final RestAuthService authService;
	
	@PostMapping("/login")
	public AuthetcationResponse login(@RequestBody LoginRequest loginRequest) {
		
		return authService.login(loginRequest);
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request){
		
		return authService.logout(request);
	}
}
