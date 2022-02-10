package com.mediacare.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@EnableWebSecurity
public class SecurityConfig{

	@Configuration
	@Order(1)
	@AllArgsConstructor
	public static class SecurityConfigForRest extends WebSecurityConfigurerAdapter{
		
		private final UserDetailsService userDetailsService;
		
		private final Filter jwtAuthenticationFilter;
		
		private final PasswordEncoder passwordEncoder;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.requestMatcher(new AntPathRequestMatcher("/api/**")).csrf().disable();
			
			http.authorizeRequests()
				.antMatchers("/api/logout","/api/login","/api/signup").permitAll()
				.anyRequest().authenticated();
			
			http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
			
		}

		@Override
		@Bean(BeanIds.AUTHENTICATION_MANAGER)
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
	
	@Configuration
	@Order(2)
	@AllArgsConstructor
	public static class SecurityConfigForMvc extends WebSecurityConfigurerAdapter{
		
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
		
			http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/admin/login","/admin/signup").permitAll()
				.antMatchers("/admin/profile").authenticated()
				.antMatchers("/admin/home").authenticated()
				.and()
					.formLogin()
					.loginPage("/admin/login")
					.usernameParameter("email")
					.passwordParameter("password")
					.loginProcessingUrl("/admin/processLogin")
					.defaultSuccessUrl("/")	
				.and()
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout", HttpMethod.GET.toString()))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
					.clearAuthentication(true)
				.and()
				.sessionManagement()
				.maximumSessions(1);
		}
		
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}

}
