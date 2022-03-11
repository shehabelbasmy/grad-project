package com.mediacare.config;

import com.mediacare.mvc.service.UserDetailsServiceMVC;
import com.mediacare.rest.exception.RestAccessDeniedHandler;
import com.mediacare.rest.exception.RestAuthHandler;
import com.mediacare.rest.service.UserDetailsServiceRest;
import com.mediacare.rest.utils.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
@AllArgsConstructor
public class SecurityConfig{

	private final PasswordEncoder passwordEncoder;

	@Configuration
	@Order(1)
	@AllArgsConstructor
	public class SecurityConfigForRest extends WebSecurityConfigurerAdapter{
		
		private final UserDetailsServiceRest userDetailsServiceRest;
		private final JwtFilter jwtFilter;
		private final RestAuthHandler restAuthFailure;
		private final RestAccessDeniedHandler restAccessHandler;
		@Override
		protected void configure(AuthenticationManagerBuilder auth){

			auth.authenticationProvider(dao());
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.requestMatcher(new AntPathRequestMatcher("/api/**"))
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/register","/api/logout","/api/refreshToken","/api/login","/api/signup").permitAll()
				.anyRequest().authenticated()
				.and()
					.logout().disable()
					.exceptionHandling()
					.authenticationEntryPoint(restAuthFailure)
					.accessDeniedHandler(restAccessHandler)
				.and()
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
		@Bean
		public AuthenticationProvider dao(){
			DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
			dao.setPasswordEncoder(passwordEncoder);
			dao.setUserDetailsService(userDetailsServiceRest);
			return dao;
		}

		@Override
		@Bean("authManagerRest")
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
	
	@Configuration
	@Order(2)
	@AllArgsConstructor
	public class SecurityConfigForMvc extends WebSecurityConfigurerAdapter{

		private final UserDetailsServiceMVC userDetailsServiceMVC;

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.headers().frameOptions().disable()
				.and()
					.csrf().disable()
					.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/login","/signup").permitAll()
					.antMatchers("/profile").authenticated()
					.antMatchers("/home").authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.usernameParameter("email")
					.passwordParameter("password")
					.loginProcessingUrl("/processLogin")
					.defaultSuccessUrl("/")
				.and()
					.exceptionHandling()
					.accessDeniedPage("/error/accessdenied")
				.and()
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.toString()))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
					.clearAuthentication(true)
				.and()
				.sessionManagement()
				.maximumSessions(1);
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.authenticationProvider(daoForMVC());
		}

		@Bean
		public AuthenticationProvider daoForMVC(){
			DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
			daoAuthenticationProvider.setUserDetailsService(userDetailsServiceMVC);
			daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
			return daoAuthenticationProvider;
		}

		@Override
		@Bean("authManagerMVC")
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}

}
