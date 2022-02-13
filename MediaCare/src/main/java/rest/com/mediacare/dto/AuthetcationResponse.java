package com.mediacare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthetcationResponse {

	private String authenticationToken;
	
	private String refreshToken;
	
	private String email;
	
	
}
