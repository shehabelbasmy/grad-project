package com.mediacare.config;

import com.mediacare.rest.exception.RestAccesDeniedHandler;
import com.mediacare.rest.exception.RestAuthHandler;
import com.mediacare.rest.utils.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true,prePostEnabled = true,securedEnabled = true)
public class SecurityConfig{

	@Configuration
	@Order(1)
	@AllArgsConstructor
	public static class SecurityConfigForRest extends WebSecurityConfigurerAdapter{
		
		private final UserDetailsService userDetailsService;
		private final JwtFilter jwtFilter;
		private final PasswordEncoder passwordEncoder;
		private final RestAuthHandler restAuthFailure;
		private final RestAccesDeniedHandler restAccessHandler;
		@Override
		protected void configure(AuthenticationManagerBuilder auth){
			auth.authenticationProvider(dao());
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.requestMatcher(new AntPathRequestMatcher("/api/**"));
			http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/logout","/api/refreshToken","/api/login","/api/signup").permitAll()
				.anyRequest().authenticated()
				.and()
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
			dao.setUserDetailsService(userDetailsService);
			return dao;
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
					.exceptionHandling()
					.accessDeniedPage("/error/accessdenied")
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

}
