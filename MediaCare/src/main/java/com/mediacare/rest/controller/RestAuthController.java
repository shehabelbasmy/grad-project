package com.mediacare.rest.controller;

import com.mediacare.mvc.dto.NewUserDto;
import com.mediacare.rest.dto.AuthenticationResponse;
import com.mediacare.rest.dto.LoginRequest;
import com.mediacare.rest.dto.RefreshTokenRequest;
import com.mediacare.rest.service.RestAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RestAuthController{

	private final RestAuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(
			@RequestBody  @Valid LoginRequest loginRequest) {

		return authService.login(loginRequest);
	}

	@PostMapping("/register")
	public AuthenticationResponse register(
			@RequestBody @Valid NewUserDto newUserDto){

		return authService.registerNewPatient(newUserDto);
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
	
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(
			@RequestBody @Valid RefreshTokenRequest reTokenRequest) {

		return authService.createNewRefreshtoken(reTokenRequest);
		
	}
}
