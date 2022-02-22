package com.mediacare.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {

	@NotBlank(message = "Is Required")
	private String jwtToken;

	@NotBlank(message = "Is Required")
	private String refreshToken;
	
}
