package com.mobileappdevelopersclub.shellp.models;

import com.google.gson.annotations.SerializedName;

public class WeatherDescritions {
	
	@SerializedName("value")
	private String value;

	public WeatherDescritions(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
