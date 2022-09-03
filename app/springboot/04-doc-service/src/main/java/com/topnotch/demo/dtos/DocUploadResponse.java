package com.topnotch.demo.dtos;

import java.io.Serializable;

public class DocUploadResponse implements Serializable{

	private String doc_name ;
	
	private String doc_type ;

	public DocUploadResponse() {
		super();
	}
	
	public DocUploadResponse(String doc_name, String doc_type) {
		super();
		this.doc_name = doc_name;
		this.doc_type = doc_type;
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
}
