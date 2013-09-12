package com.mobileappdevelopersclub.shellp.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
	
	@SerializedName("data")
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
//	{ "data": { "current_condition": [ {"cloudcover": "0", "humidity": "77", "observation_time": "02:35 PM", "precipMM": "0.0", "pressure": "1021", "temp_C": "29", "temp_F":   "83", "visibility": "16", "weatherCode": "113",  "weatherDesc": [ {"value": "Sunny" } ],  "weatherIconUrl": [ {"value": "http:\/\/cdn.worldweatheronline.net\/images\/wsymbols01_png_64\/wsymbol_0001_sunny.png" } ], "winddir16Point": "N", "winddirDegree": "0", "windspeedKmph": "0", "windspeedMiles": "0" } ],  "request": [ {"query": "20740", "type": "Zipcode" } ],  "weather": [ {"date": "2013-09-11", "precipMM": "0.0", "tempMaxC": "34", "tempMaxF": "93", "tempMinC": "21", "tempMinF": "70", "weatherCode": "113",  "weatherDesc": [ {"value": "Sunny" } ],  "weatherIconUrl": [ {"value": "http:\/\/cdn.worldweatheronline.net\/images\/wsymbols01_png_64\/wsymbol_0001_sunny.png" } ], "winddir16Point": "S", "winddirDegree": "186", "winddirection": "S", "windspeedKmph": "15", "windspeedMiles": "10" }, {"date": "2013-09-12", "precipMM": "4.6", "tempMaxC": "34", "tempMaxF": "93", "tempMinC": "21", "tempMinF": "69", "weatherCode": "113",  "weatherDesc": [ {"value": "Sunny" } ],  "weatherIconUrl": [ {"value": "http:\/\/cdn.worldweatheronline.net\/images\/wsymbols01_png_64\/wsymbol_0001_sunny.png" } ], "winddir16Point": "SSW", "winddirDegree": "211", "winddirection": "SSW", "windspeedKmph": "13", "windspeedMiles": "8" }, {"date": "2013-09-13", "precipMM": "1.4", "tempMaxC": "27", "tempMaxF": "80", "tempMinC": "10", "tempMinF": "50", "weatherCode": "176",  "weatherDesc": [ {"value": "Patchy rain nearby" } ],  "weatherIconUrl": [ {"value": "http:\/\/cdn.worldweatheronline.net\/images\/wsymbols01_png_64\/wsymbol_0009_light_rain_showers.png" } ], "winddir16Point": "NNW", "winddirDegree": "334", "winddirection": "NNW", "windspeedKmph": "18", "windspeedMiles": "11" }, {"date": "2013-09-14", "precipMM": "0.0", "tempMaxC": "20", "tempMaxF": "67", "tempMinC": "8", "tempMinF": "46", "weatherCode": "113",  "weatherDesc": [ {"value": "Sunny" } ],  "weatherIconUrl": [ {"value": "http:\/\/cdn.worldweatheronline.net\/images\/wsymbols01_png_64\/wsymbol_0001_sunny.png" } ], "winddir16Point": "NNW", "winddirDegree": "346", "winddirection": "NNW", "windspeedKmph": "22", "windspeedMiles": "14" }, {"date": "2013-09-15", "precipMM": "0.0", "tempMaxC": "20", "tempMaxF": "69", "tempMinC": "12", "tempMinF": "54", "weatherCode": "113",  "weatherDesc": [ {"value": "Sunny" } ],  "weatherIconUrl": [ {"value": "http:\/\/cdn.worldweatheronline.net\/images\/wsymbols01_png_64\/wsymbol_0001_sunny.png" } ], "winddir16Point": "N", "winddirDegree": "349", "winddirection": "N", "windspeedKmph": "12", "windspeedMiles": "8" } ] }}

}
