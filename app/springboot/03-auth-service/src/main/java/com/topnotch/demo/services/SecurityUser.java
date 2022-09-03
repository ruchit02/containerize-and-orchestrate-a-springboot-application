package com.topnotch.demo.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {

	private String password;
	private String username;

	private List<GrantedAuthority> gAuthorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return gAuthorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.gAuthorities = authorities;
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
