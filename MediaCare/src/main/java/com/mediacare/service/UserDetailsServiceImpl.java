package com.mediacare.service;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mediacare.dao.UserRepository;
import com.mediacare.entity.MyUser;

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
		
		return buildSpringUser(user);
	}

	private User buildSpringUser(MyUser user) {
		
		SimpleGrantedAuthority simpleGrantedAuthority = 
				new SimpleGrantedAuthority(user.getAuthority().toString());
		
		Collection< ? extends GrantedAuthority> roles= 
				Collections.singletonList(simpleGrantedAuthority);
		
		User springUser = new User(
				user.getEmail(), 
				user.getPassword(), 
				user.isEnabled(), 
				true, 
				true, 
				true, 
				roles);
		
		return springUser;
	}
	
}
