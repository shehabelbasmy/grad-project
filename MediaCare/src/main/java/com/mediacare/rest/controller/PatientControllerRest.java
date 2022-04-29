package com.mediacare.rest.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mediacare.rest.dao.PatientRepository.UserPorition;
import com.mediacare.rest.service.UserDetailsServiceRest;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PatientControllerRest {

	private final UserDetailsServiceRest userDetailsServiceRest;
	
	@GetMapping("/profile")
	public UserPorition getCurrentUser() {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userDetailsServiceRest.getUserByEmail(email);
	}
}
