package com.mediacare.util;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mediacare.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpringUser implements UserDetails {

	@Getter
	private User user;

	private String firstName ;
	private String lastName;
	
	private int id;
	public SpringUser(User user) {
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		SimpleGrantedAuthority simpleAuthority =
				new SimpleGrantedAuthority(user.getAuthority().toString());
		
		return Collections.singletonList(simpleAuthority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled();
	}

	public int getId() {
		return user.getId();
	}

	public String getFirstName() {
		return this.user.getFirstName();
	}

	public String getLastName() {
		return user.getLastName();
	}

}
