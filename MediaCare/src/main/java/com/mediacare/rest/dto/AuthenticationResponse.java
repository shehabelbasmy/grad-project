package com.mediacare.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

	private String authenticationToken;
	
	private String refreshToken;
}
