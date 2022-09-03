package com.topnotch.demo.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="users")
public class DBUser implements Serializable {
	
	//PRIMARY KEY
	@Id
	@Column(name="email")
	private String username;
	
	@Column(name="pass_word")
	private String pass_word;
	
	//RELATION
	@OneToMany(mappedBy="username", fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<Authority> authorities;
	
	
	public DBUser() { super(); }
	
	public DBUser(String username, String pass_word, List<Authority> authorities) {
		super();
		this.username = username;
		this.pass_word = pass_word;
		this.authorities = authorities;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass_word() {
		return pass_word;
	}

	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
}
