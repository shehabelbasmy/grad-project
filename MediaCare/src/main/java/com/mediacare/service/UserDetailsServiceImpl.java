package com.mediacare.service;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mediacare.dao.UserRepository;
import com.mediacare.entity.MyUser;
import com.mediacare.util.SpringUser;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepo;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<MyUser> userOptional = userRepo.findByEmail(email);
		
		MyUser user=userOptional
				.orElseThrow(()->new UsernameNotFoundException("No User Found with Email : "+email));
		user.setLogggedOut(false);
		
		userRepo.saveAndFlush(user);
		
		return new SpringUser(user);
	}

	@Transactional(readOnly = true)
	public SpringUser getUserByEmail(String email) {
		
		Optional<MyUser> userOptional = userRepo.findByEmail(email);
		
		MyUser user=userOptional
				.orElseThrow(()->new UsernameNotFoundException("No User Found with Email : "+email));
		
		return new SpringUser(user);
	}

}
