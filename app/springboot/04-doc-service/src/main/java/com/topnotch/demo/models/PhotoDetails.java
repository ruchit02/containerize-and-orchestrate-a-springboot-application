package com.topnotch.demo.models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="photo_details")
public class PhotoDetails implements Serializable{

	//PRIMARY KEY
	@Id
	@Column(name="photo_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long photo_id ;
	
	@Column(name="photo_name")
	private String photo_name;
	
	@Column(name="width")
	private Integer width ;
	
	@Column(name="height")
	private Integer height ;
	
	@Column(name="avg_color")
	private String avg_color ;
	
	@Column(name="times_sold")
	private Integer times_sold ;
	
	//FOREIGN KEY
	@ManyToOne
	@JoinColumn(name="photographer_id", referencedColumnName="photographer_id")
	private PhotographerDetails photographer_id ;
	
	//RELATION
	@JsonIgnore
	@OneToOne(mappedBy="photo_id", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@NotFound(action = NotFoundAction.IGNORE)
	private PhotoAvailableSizesDetails availableSizes ;
	
	public PhotoDetails() {
		super();
	}

	public PhotoDetails(Long photo_id, String photo_name, Integer width, Integer height, String avg_color,
			Integer times_sold, PhotographerDetails photographer_id, PhotoAvailableSizesDetails availableSizes) {
		super();
		this.photo_id = photo_id;
		this.photo_name = photo_name;
		this.width = width;
		this.height = height;
		this.avg_color = avg_color;
		this.times_sold = times_sold;
		this.photographer_id = photographer_id;
		this.availableSizes = availableSizes;
	}
	
	public Long getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(Long photo_id) {
		this.photo_id = photo_id;
	}

	public String getPhoto_name() {
		return photo_name;
	}

	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getAvg_color() {
		return avg_color;
	}

	public void setAvg_color(String avg_color) {
		this.avg_color = avg_color;
	}

	public PhotoAvailableSizesDetails getAvailableSizes() {
		return availableSizes;
	}

	public void setAvailableSizes(PhotoAvailableSizesDetails availableSizes) {
		this.availableSizes = availableSizes;
	}

	public PhotographerDetails getPhotographer_id() {
		return photographer_id;
	}

	public void setPhotographer_id(PhotographerDetails photographer_id) {
		this.photographer_id = photographer_id;
	}

	public Integer getTimes_sold() {
		return times_sold;
	}

	public void setTimes_sold(Integer times_sold) {
		this.times_sold = times_sold;
	}
	
	@Override
	public String toString() {
		return "PhotoDetails [\nphoto_id=" + photo_id + ", \nphoto_name=" + photo_name + ", \nwidth=" + width + ", \nheight="
				+ height + ", \navg_color=" + avg_color + ", \ntimes_sold=" + times_sold + ", \nphotographer_id="
				+ photographer_id.getPhotographer_id() + ", \navailableSizes=" + availableSizes + "]";
	}
}
