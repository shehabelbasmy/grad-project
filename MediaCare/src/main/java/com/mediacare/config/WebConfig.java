package com.mediacare.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer{

	private final LocaleChangeInterceptor localeChangeInterceptor;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error/accessdenied").setViewName("access-denied");
		WebMvcConfigurer.super.addViewControllers(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor);
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
