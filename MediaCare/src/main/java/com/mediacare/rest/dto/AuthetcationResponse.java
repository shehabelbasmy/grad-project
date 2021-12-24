package com.mediacare.rest.dto;

import java.time.Instant;

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
	
	private Instant expiredAt;
	
	private String email;
	
	
}
