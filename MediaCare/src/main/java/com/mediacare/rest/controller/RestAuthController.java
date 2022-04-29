package com.mediacare.rest.controller;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediacare.mvc.dto.NewUserDto;
import com.mediacare.rest.dto.LoginRequest;
import com.mediacare.rest.dto.RefreshTokenRequest;
import com.mediacare.rest.service.RestAuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RestAuthController{

	private final RestAuthService authService;

	@PostMapping("/login")
	public Map<String, String> login(
			@RequestBody  @Valid LoginRequest loginRequest) {
		String jwt = authService.login(loginRequest);
		return Collections.singletonMap("authenticationToken", jwt);
	}

	@PostMapping("/register")
	public Map<String, String> register(
			@RequestBody @Valid NewUserDto newUserDto){
		String jwt = authService.registerNewPatient(newUserDto); 
		
		return Collections.singletonMap("authenticationToken", jwt); 
	}

	@GetMapping("/test")
	@Secured("PATIENT")
	public String test() {

		return "Hello From Test Endpoint";
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(
			@RequestBody @Valid RefreshTokenRequest request){
		
 			return authService.logout(request);
	}

}
