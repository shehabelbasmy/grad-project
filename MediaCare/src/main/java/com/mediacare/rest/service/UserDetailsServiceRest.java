package com.mediacare.rest.service;

import com.mediacare.entity.Patient;
import com.mediacare.rest.dao.PatientRepository;
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
public class UserDetailsServiceRest implements UserDetailsService {

    private final PatientRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Patient> userOptional = userRepo.findByEmail(email);

        Patient user=userOptional
                .orElseThrow(()-> new UsernameNotFoundException("Username Not Found"));
        return new SpringUser(user);
    }
}
