package com.topnotch.demo.models;

import java.io.Serializable;
import java.util.Objects;

public class AuthorityKey implements Serializable {
	
private String username;
	
	private String authority;

	
	
	
	public AuthorityKey() { super(); }
	
	public AuthorityKey(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}
	
	

	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authority, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorityKey other = (AuthorityKey) obj;
		return Objects.equals(authority, other.authority) && Objects.equals(username, other.username);
	}

}
