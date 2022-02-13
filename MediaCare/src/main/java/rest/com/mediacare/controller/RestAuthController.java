package com.mediacare.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediacare.dto.AuthetcationResponse;
import com.mediacare.dto.LoginRequest;
import com.mediacare.dto.RefreshTokenRequest;
import com.mediacare.service.RestAuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RestAuthController {

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
	public ResponseEntity<?> logout(
			@RequestBody @Valid RefreshTokenRequest reqest,
			BindingResult bindResult){
		
		if (bindResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return authService.logout(reqest);
	}
	
	@PostMapping("/refreshToken")
	public ResponseEntity<AuthetcationResponse> refreshToken(
			@RequestBody @Valid RefreshTokenRequest reTokenRequest,
			BindingResult bindResult) {
		if (bindResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return authService.createNewFreshtoken(reTokenRequest);
		
	}
}
