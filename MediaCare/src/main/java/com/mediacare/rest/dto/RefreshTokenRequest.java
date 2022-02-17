package com.mediacare.rest.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

	@NotBlank
	private String refreshToken;
	
	@NotBlank
	private String jwtToken;
	
}
