package com.topnotch.demo.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginForm implements Serializable {

	@NotNull
	@NotEmpty
	private String username ;
	
	@NotNull
	@NotEmpty
	private String password ;
	
	public LoginForm() {
		super();
	}

	public LoginForm(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
