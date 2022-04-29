package com.mediacare.rest.service;

import javax.transaction.Transactional;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mediacare.entity.Patient;
import com.mediacare.enums.Authority;
import com.mediacare.mapper.UserMapper;
import com.mediacare.mvc.dto.NewUserDto;
import com.mediacare.rest.dao.PatientRepository;
import com.mediacare.rest.dto.LoginRequest;
import com.mediacare.rest.dto.RefreshTokenRequest;
import com.mediacare.rest.utils.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestAuthService {

	private final PatientRepository patientRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final MessageSource messagesource;
	private final UserMapper userMapper;
	private final AuthenticationManager authManagerRest;

	@Transactional
	public String registerNewPatient(NewUserDto newUser) {

		Patient patient = userMapper.newUserToPatient(newUser);
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		patientRepository.save(patient);
		String jwtToken=jwtProvider.generateToken(newUser.getEmail(),patient.getAuthority());

		return jwtToken;
	}

	public String login(LoginRequest loginRequest) {

		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
				loginRequest.getPassword());
		Authentication authentication = this.authManagerRest.authenticate(userToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Authority authority = Authority.valueOf(authentication.getAuthorities().iterator().next().getAuthority());
		String jwtToken = jwtProvider.generateToken(loginRequest.getEmail(),authority);
		return jwtToken;
	}

	public ResponseEntity<?> logout(RefreshTokenRequest refreshRequest) {
		jwtProvider.invalidateToken(refreshRequest.getJwtToken());
		String message=new MessageSourceAccessor(messagesource).getMessage("logoutmessage");
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

}
