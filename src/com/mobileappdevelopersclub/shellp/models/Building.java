package com.mobileappdevelopersclub.shellp.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class Building {
	
	@SerializedName("number")
	private String buildingNumber;

	@SerializedName("x")
	private String xCoord;
	
	@SerializedName("y")
	private String yCoord;
	
	@SerializedName("name_short")
	private String buildingNameShort;
	
	@SerializedName("name_long")
	private String buildingNameLong;

	public Building(String buildingNumber, String xCoord, String yCoord,
			String buildingNameShort, String buildingNameLong) {
		super();
		this.buildingNumber = buildingNumber;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.buildingNameShort = buildingNameShort;
		this.buildingNameLong = buildingNameLong;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getxCoord() {
		return xCoord;
	}

	public void setxCoord(String xCoord) {
		this.xCoord = xCoord;
	}

	public String getyCoord() {
		return yCoord;
	}

	public void setyCoord(String yCoord) {
		this.yCoord = yCoord;
	}

	public String getBuildingNameShort() {
		return buildingNameShort;
	}

	public void setBuildingNameShort(String buildingNameShort) {
		this.buildingNameShort = buildingNameShort;
	}

	public String getBuildingNameLong() {
		return buildingNameLong;
	}

	public void setBuildingNameLong(String buildingNameLong) {
		this.buildingNameLong = buildingNameLong;
	}
	
	public LatLng getBuildingLatLng() {
		return new LatLng( Double.parseDouble(yCoord), Double.parseDouble(xCoord));
	}
}
