package com.mediacare.beans;

import java.util.Arrays;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class AppBeans {

    @Bean
    public LocaleResolver localeResolver() {
        final AcceptHeaderLocaleResolver slr= new AcceptHeaderLocaleResolver();
        Locale arabic = new Locale.Builder()
                .setLanguage("ar")
                .setScript("Arab")
                .setRegion("EG")
                .build();
        slr.setSupportedLocales(Arrays.asList(Locale.ENGLISH, arabic));
        return slr;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Queue<String> jwtQueue(){
        return new ConcurrentLinkedDeque<>();
    }
    
    @Bean
    public WebClient ml() {
        return WebClient.create("http://localhost:5000/");
    }
}
