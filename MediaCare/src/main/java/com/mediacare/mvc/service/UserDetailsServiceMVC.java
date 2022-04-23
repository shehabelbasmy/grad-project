package com.mediacare.mvc.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mediacare.entity.Patient;
import com.mediacare.rest.dao.PatientRepository;
import com.mediacare.util.SpringUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceMVC implements UserDetailsService {
	
	private final PatientRepository userRepo;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email){
		
		Optional<Patient> userOptional = userRepo.findByEmail(email);
		
		Patient patient=userOptional
				.orElseThrow(()-> new UsernameNotFoundException("Username Not Found"));
		
		return new SpringUser(patient);
	}

}
