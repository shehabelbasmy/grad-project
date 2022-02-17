package com.mediacare.service;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
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
				.orElseThrow(()-> new BadCredentialsException("Incorrect User Name Or Password"));
//		MyUser user=userOptional
//				.orElseThrow(()->{
//					var ex =new MediaCareException("Incorrect User Name Or Password");
//					ex.setStatus(HttpStatus.UNAUTHORIZED);
//					return ex;
//				});
		return new SpringUser(user);
	}

}
