package com.mediacare.mvc.service;

import com.mediacare.dao.UserRepository;
import com.mediacare.entity.MyUser;
import com.mediacare.mapper.MyUserMapper;
import com.mediacare.mvc.dto.NewUserDto;
import com.mediacare.rest.dto.AuthenticationResponse;
import com.mediacare.rest.service.RefreshTokenService;
import com.mediacare.rest.utils.JwtProvider;
import com.mediacare.util.SpringUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class MvcAuthService{
    private final UserRepository userRepository;
    private final MyUserMapper myUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void register(NewUserDto newUser) {

        MyUser entity = myUserMapper.newUserToEntityUser(newUser);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity=userRepository.save(entity);
        UsernamePasswordAuthenticationToken userToken =
                new UsernamePasswordAuthenticationToken(newUser.getEmail(),
                newUser.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(userToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
