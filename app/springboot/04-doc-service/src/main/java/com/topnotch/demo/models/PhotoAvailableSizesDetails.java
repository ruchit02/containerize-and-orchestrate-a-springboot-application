package com.topnotch.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="photo_available_sizes")
public class PhotoAvailableSizesDetails {

	//PRIMARY KEY
	@Id
	@Column(name="photo_size_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long photo_size_id ;
	
	@Column(name="original")
	private String original ;
	
	@Column(name="large")
	private String large ;
	
	@Column(name="large2x")
	private String large2x ;
	
	@Column(name="medium_")
	private String medium ;
	
	@Column(name="small")
	private String small ;
	
	@Column(name="portrait")
	private String portrait ;
	
	@Column(name="landscape")
	private String landscape ;
	
	@Column(name="tiny")
	private String tiny ;
	
	//FOREIGN KEY
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="photo_id", referencedColumnName="photo_id")
	private PhotoDetails photo_id;

	public PhotoAvailableSizesDetails() {
		super();
	}
	
	public PhotoAvailableSizesDetails(Long photo_size_id, String original, String large, String large2x, String medium,
			String small, String portrait, String landscape, String tiny, PhotoDetails photo_id) {
		super();
		this.photo_size_id = photo_size_id;
		this.original = original;
		this.large = large;
		this.large2x = large2x;
		this.medium = medium;
		this.small = small;
		this.portrait = portrait;
		this.landscape = landscape;
		this.tiny = tiny;
		this.photo_id = photo_id;
	}

	public Long getPhoto_size_id() {
		return photo_size_id;
	}

	public void setPhoto_size_id(Long photo_size_id) {
		this.photo_size_id = photo_size_id;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getLarge2x() {
		return large2x;
	}

	public void setLarge2x(String large2x) {
		this.large2x = large2x;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

	public String getTiny() {
		return tiny;
	}

	public void setTiny(String tiny) {
		this.tiny = tiny;
	}

	public PhotoDetails getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(PhotoDetails photo_id) {
		this.photo_id = photo_id;
	}

	@Override
	public String toString() {
		return "PhotoAvailableSizesDetails [\nphoto_size_id=" + photo_size_id + ", \noriginal=" + original + ", \nlarge="
				+ large + ", \nlarge2x=" + large2x + ", \nmedium=" + medium + ", \nsmall=" + small + ", \nportrait=" + portrait
				+ ", \nlandscape=" + landscape + ", \ntiny=" + tiny + ", \nphoto_id=" + photo_id.getPhoto_id() + "]";
	}
}
