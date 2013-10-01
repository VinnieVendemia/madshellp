package com.mobileappdevelopersclub.shellp.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BuildingsResponse {

	@SerializedName("Buildings")
	private List<Building> buildings;

	public BuildingsResponse(List<Building> buildings) {
		super();
		this.buildings = buildings;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}
	
	
	
}
