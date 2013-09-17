package com.mobileappdevelopersclub.shellp.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UMDClassResponse {
	
	
	private final static String URL = "DJANGO_URL";
	
	@SerializedName("Classes")
	private List<UMDClass> classes;

	public UMDClassResponse(List<UMDClass> classes) {
		super();
		this.classes = classes;
	}
	
	public static String getUserUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(URL);
		//TODO: append user info
		
		return sb.toString();
	}

	public List<UMDClass> getClasses() {
		return classes;
	}

	public void setClasses(List<UMDClass> classes) {
		this.classes = classes;
	}
	
	
	
}
