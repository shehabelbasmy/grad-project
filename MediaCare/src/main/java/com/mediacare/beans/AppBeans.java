package com.mediacare.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Configuration
public class AppBeans {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Queue<String> jwtQueue(){
        return new ConcurrentLinkedDeque<>();
    }



}
