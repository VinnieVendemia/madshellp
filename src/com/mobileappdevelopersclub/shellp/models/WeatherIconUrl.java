package com.mobileappdevelopersclub.shellp.models;

import com.google.gson.annotations.SerializedName;

public class WeatherIconUrl {
	
	@SerializedName("value")
	private String iconUrl;

	public WeatherIconUrl(String iconUrl) {
		super();
		this.iconUrl = iconUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	
}
