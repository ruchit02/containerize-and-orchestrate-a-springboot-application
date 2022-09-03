package com.topnotch.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employeedocuments")
public class EmployeeDocuments {

	// PRIMARY KEY
	@Id
	@Column(name = "document_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "doc_name")
	private String doc_name;

	@Column(name = "doc_type")
	private String doc_type;

	@Lob
	@Column(name = "doc_data")
	private byte[] data;

	// FOREIGN KEY
	@ManyToOne
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
	private EmployeeDetails emp_id;

	public EmployeeDocuments() {
		super();
	}

	public EmployeeDocuments(Long id, String doc_name, String doc_type, byte[] data, EmployeeDetails emp_id) {
		super();
		this.id = id;
		this.doc_name = doc_name;
		this.doc_type = doc_type;
		this.data = data;
		this.emp_id = emp_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDoc_name() {
		return doc_name;
	}

	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public EmployeeDetails getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(EmployeeDetails emp_id) {
		this.emp_id = emp_id;
	}
}
