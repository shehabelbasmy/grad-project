package com.mediacare.mvc.service;

import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mediacare.entity.Patient;
import com.mediacare.enums.Authority;
import com.mediacare.mapper.UserMapper;
import com.mediacare.mvc.dto.NewUserDto;
import com.mediacare.rest.dao.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MvcAuthService{
    private final PatientRepository patientRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authManagerMVC;

    @Transactional
    public void registerNewAdmin(NewUserDto newUser) {
        Patient patient = userMapper.newUserToPatient(newUser);
        patient.setPassword(passwordEncoder.encode(newUser.getPassword()));
        patientRepository.save(patient);
        UsernamePasswordAuthenticationToken userToken =
                new UsernamePasswordAuthenticationToken(newUser.getEmail(),
                newUser.getPassword(),
                getAuthority(patient.getAuthority()));
//        Authentication authentication = this.authManagerMVC.authenticate(userToken);
        SecurityContextHolder.getContext().setAuthentication(userToken);
    }
    
    private Collection<? extends GrantedAuthority> getAuthority(Authority authority2){
    	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authority2.toString());
    	return Collections.singletonList(authority);
    }
}
