package com.mobileappdevelopersclub.shellp.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CurrentCondition {
	
	
	@SerializedName("humidity")
	private String humidity;
	
	@SerializedName("temp_F")
	private String currentTemp;
	

	public CurrentCondition(String humidity, String currentTemp) {
		super();
		this.humidity = humidity;
		this.currentTemp = currentTemp;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}
	
	
}
