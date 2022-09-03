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
@Table(name = "employeedetails")
public class EmployeeDetails {

	@Id
	@Column(name = "emp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String first_name;

	@Column(name = "last_name")
	private String last_name;

	@Column(name = "department")
	private String department;

	@Column(name = "expertise")
	private String expertise;

	@Column(name = "email")
	private String email;

	// RELATION
	@JsonIgnore
	@OneToMany(mappedBy = "emp_id", fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	private List<EmployeeDocuments> documents;

	public EmployeeDetails() {
		super();
	}

	public EmployeeDetails(Long id, String first_name, String last_name, String department, String expertise,
			String email, List<EmployeeDocuments> documents) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.department = department;
		this.expertise = expertise;
		this.email = email;
		this.documents = documents;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<EmployeeDocuments> getDocuments() {
		return documents;
	}

	public void setDocuments(List<EmployeeDocuments> documents) {
		this.documents = documents;
	}
}
