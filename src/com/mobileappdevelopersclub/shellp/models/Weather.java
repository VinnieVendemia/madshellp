package com.mobileappdevelopersclub.shellp.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Weather {
	
	@SerializedName("date")
	private String date;
	
	@SerializedName("tempMaxF")
	private String tempMax;
	
	@SerializedName("tempMinF")
	private String tempMin;
	
	@SerializedName("weatherIconUrl")
	private List<WeatherIconUrl> weatherIconList;
	
	@SerializedName("weatherDesc")
	private List<WeatherDescritions> desctiptions;

	public Weather(String date, String tempMax, String tempMin,
			List<WeatherIconUrl> weatherIconList,
			List<WeatherDescritions> desctiptions) {
		super();
		this.date = date;
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.weatherIconList = weatherIconList;
		this.desctiptions = desctiptions;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTempMax() {
		return tempMax;
	}

	public void setTempMax(String tempMax) {
		this.tempMax = tempMax;
	}

	public String getTempMin() {
		return tempMin;
	}

	public void setTempMin(String tempMin) {
		this.tempMin = tempMin;
	}

	public List<WeatherIconUrl> getWeatherIconList() {
		return weatherIconList;
	}

	public void setWeatherIconList(List<WeatherIconUrl> weatherIconList) {
		this.weatherIconList = weatherIconList;
	}

	public List<WeatherDescritions> getDesctiptions() {
		return desctiptions;
	}

	public void setDesctiptions(List<WeatherDescritions> desctiptions) {
		this.desctiptions = desctiptions;
	}
	
	
	
}
