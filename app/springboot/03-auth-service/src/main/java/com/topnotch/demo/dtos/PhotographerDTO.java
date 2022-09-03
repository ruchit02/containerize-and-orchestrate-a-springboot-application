package com.topnotch.demo.dtos;

import java.io.Serializable;
import java.util.Objects;

public class PhotographerDTO implements Serializable{

	private String username ;
	
	private String first_name;
	
	private String last_name ;
	
	public PhotographerDTO() {
		super();
	}

	public PhotographerDTO(String username, String first_name, String last_name) {
		super();
		this.username = username;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(first_name, last_name, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhotographerDTO other = (PhotographerDTO) obj;
		return Objects.equals(first_name, other.first_name) && Objects.equals(last_name, other.last_name)
				&& Objects.equals(username, other.username);
	}
}
