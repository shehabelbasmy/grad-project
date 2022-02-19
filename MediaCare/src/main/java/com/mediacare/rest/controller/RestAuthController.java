package com.mediacare.rest.controller;

import com.mediacare.rest.dto.AuthenticationResponse;
import com.mediacare.rest.dto.LoginRequest;
import com.mediacare.rest.dto.RefreshTokenRequest;
import com.mediacare.service.RestAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Valid
public class RestAuthController{

	private final RestAuthService authService;

	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest
			,BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		return authService.login(loginRequest);
	}

	@GetMapping("/test")
	@Secured("PATIENT")
	public String test() {

		return "test";
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(
			@RequestBody RefreshTokenRequest request,
			BindingResult bindResult){
		
		if (bindResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return authService.logout(request);
	}
	
	@PostMapping("/refreshToken")
	public ResponseEntity<AuthenticationResponse> refreshToken(
			@RequestBody RefreshTokenRequest reTokenRequest,
			BindingResult bindResult) {
		if (bindResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return authService.createNewRefreshtoken(reTokenRequest);
		
	}
}
