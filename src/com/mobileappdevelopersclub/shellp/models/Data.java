package com.mobileappdevelopersclub.shellp.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Data {
	
	@SerializedName("current_condition")
	private List<CurrentCondition> currCondition;
	
	@SerializedName("weather")
	private List<Weather> weather;

	public List<Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public List<CurrentCondition> getCurrCondition() {
		return currCondition;
	}

	public void setCurrCondition(List<CurrentCondition> currCondition) {
		this.currCondition = currCondition;
	}
}
