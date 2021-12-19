package com.mediacare.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mediacare.dao.UserRepository;
import com.mediacare.dto.NewUserForm;
import com.mediacare.entity.MyUser;
import com.mediacare.enums.Authority;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional
	public void register(NewUserForm newUser) {

		userRepo.save(buildUserEntity(newUser));
	}
	
	private MyUser buildUserEntity(NewUserForm newUser) {
		
		return MyUser.builder()
			  .authority(Authority.ADMIN)
			  .email(newUser.getEmail())
			  .password(passwordEncoder.encode(newUser.getPassword()))
			  .firstName(newUser.getFirstName())
			  .lastName(newUser.getLastName())
			  .enabled(true)
			  .build();
		
		
	}

}
