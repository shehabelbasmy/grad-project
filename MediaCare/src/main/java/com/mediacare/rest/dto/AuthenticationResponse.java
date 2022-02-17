package com.mediacare.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

	private String authenticationToken;
	
	private String refreshToken;
	
	private String email;
	
	
}
