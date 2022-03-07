package com.mediacare.mvc.service;

import com.mediacare.entity.Admin;
import com.mediacare.mapper.UserMapper;
import com.mediacare.mvc.dao.AdminRepository;
import com.mediacare.mvc.dto.NewUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MvcAuthService{
    private final AdminRepository adminRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManagerMVC;

    @Transactional
    public void registerNewAdmin(NewUserDto newUser) {
        Admin admin = userMapper.newUserToAdmin(newUser);
        admin.setPassword(passwordEncoder.encode(newUser.getPassword()));
        adminRepository.save(admin);
        UsernamePasswordAuthenticationToken userToken =
                new UsernamePasswordAuthenticationToken(newUser.getEmail(),
                newUser.getPassword());
        Authentication authentication = this.authManagerMVC.authenticate(userToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
