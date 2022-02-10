package com.mediacare.service;

import java.time.Instant;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mediacare.dao.UserRepository;
import com.mediacare.dto.AuthetcationResponse;
import com.mediacare.dto.LoginRequest;
import com.mediacare.dto.NewUserForm;
import com.mediacare.entity.MyUser;
import com.mediacare.enums.Authority;
import com.mediacare.util.SpringUser;
import com.mediacare.utils.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestAuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
//	private final RefreshTokenService refreshTokenService;
	
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

	@Transactional
	public AuthetcationResponse login(LoginRequest loginRequest) {
		
		UsernamePasswordAuthenticationToken userToken =
				new UsernamePasswordAuthenticationToken(
						loginRequest.getEmail(), 
						loginRequest.getPassword());
		
		Authentication authentication = this.authenticationManager.authenticate(userToken);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		return AuthetcationResponse
				.builder()
				.email(loginRequest.getEmail())
				.refreshToken(null)
				.authenticationToken(token)
				.expiredAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationMills()))
				.build();
	}
	
	@Transactional
	public ResponseEntity<?> logout(HttpServletRequest request) {
		
		String authorization = request.getHeader("Authorization");
		
		if (authorization.startsWith("Bearer ")) {
			String token = authorization.substring(7);
			
			Optional<MyUser> optionalUser= userRepo.findByEmail(jwtProvider.getEmailFromJwt(token));
			
			if(optionalUser.isPresent()) {
				MyUser theUser = optionalUser.get();
				theUser.setLogggedOut(true);
				userRepo.saveAndFlush(theUser);
			}
			
			return ResponseEntity.ok(HttpStatus.ACCEPTED);	
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
