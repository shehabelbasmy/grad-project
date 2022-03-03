package com.mediacare.mvc.service;

import com.mediacare.entity.Admin;
import com.mediacare.mvc.dao.AdminRepository;
import com.mediacare.util.SpringUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceMVC implements UserDetailsService {
	
	private final AdminRepository userRepo;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email){
		
		Optional<Admin> userOptional = userRepo.findByEmail(email);
		
		Admin admin=userOptional
				.orElseThrow(()-> new UsernameNotFoundException("Username Not Found"));
		return new SpringUser(admin);
	}

}
