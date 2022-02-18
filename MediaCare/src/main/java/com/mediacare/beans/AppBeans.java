package com.mediacare.beans;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Configuration
public class AppBeans {

    @Bean
    public MessageSource messageSource(){
        SpringSecurityMessageSource source = new SpringSecurityMessageSource();
        source.setBasename("messages");
        source.setCacheSeconds(0);
        source.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Queue<String> jwtQueue(){
        return new ConcurrentLinkedDeque<>();
    }



}
