package com.topnotch.demo.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "photographer_details")
public class PhotographerDetails {

	// PRIMARY KEY
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "photographer_id")
	private Long photographer_id;

	@Column(name = "photographer")
	private String photographer;

	@Column(name = "email")
	private String email;
	
	@Column(name = "photographer_url")
	private String photographer_url;

	// RELATION
	@JsonIgnore
	@OneToMany(mappedBy = "photographer_id", fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<PhotoDetails> photos;

	public PhotographerDetails() {
		super();
	}
	
	public PhotographerDetails(Long photographer_id, String photographer, String email, String photographer_url,
			List<PhotoDetails> photos) {
		super();
		this.photographer_id = photographer_id;
		this.photographer = photographer;
		this.email = email;
		this.photographer_url = photographer_url;
		this.photos = photos;
	}

	public Long getPhotographer_id() {
		return photographer_id;
	}

	public void setPhotographer_id(Long photographer_id) {
		this.photographer_id = photographer_id;
	}

	public String getPhotographer() {
		return photographer;
	}

	public void setPhotographer(String photographer) {
		this.photographer = photographer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotographer_url() {
		return photographer_url;
	}

	public void setPhotographer_url(String photographer_url) {
		this.photographer_url = photographer_url;
	}

	public List<PhotoDetails> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoDetails> photos) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "PhotographerDetails [\nphotographer_id=" + photographer_id + ", \nphotographer=" + photographer + ", \nemail="
				+ email + ", \nphotographer_url=" + photographer_url + ", \nphotos=" + photos + "]";
	}
}
