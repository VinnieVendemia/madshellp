package com.mobileappdevelopersclub.shellp.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UMDClassResponse {
	
	
	@SerializedName("Classes")
	private List<UMDClass> classes;

	public UMDClassResponse(List<UMDClass> classes) {
		super();
		this.classes = classes;
	}

	public List<UMDClass> getClasses() {
		return classes;
	}

	public void setClasses(List<UMDClass> classes) {
		this.classes = classes;
	}
	
	
	
}
