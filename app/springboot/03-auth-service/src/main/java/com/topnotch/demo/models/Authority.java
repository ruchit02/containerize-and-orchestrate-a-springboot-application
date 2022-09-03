package com.topnotch.demo.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_authorities")
@IdClass(AuthorityKey.class)
public class Authority implements Serializable {

	// FOREIGN KEY
	@Id
	@ManyToOne
	@JoinColumn(name = "email", referencedColumnName = "email")
	private DBUser username;

	// PRIMARY KEY
	@Id
	@Column(name = "authority")
	private String authority;

	public Authority() {
		super();
	}

	public Authority(DBUser username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}

	public DBUser getUsername() {
		return username;
	}

	public void setUsername(DBUser username) {
		this.username = username;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
