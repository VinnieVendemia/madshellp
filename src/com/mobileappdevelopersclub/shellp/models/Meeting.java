package com.mobileappdevelopersclub.shellp.models;

import com.google.gson.annotations.SerializedName;

public class Meeting {
	
	@SerializedName("building")
	private String building;
	
	@SerializedName("roomNumber")
	private String roomNumber;
	
	@SerializedName("timeStart")
	private String timeStart;
	
	@SerializedName("timeEnd")
	private String timeEnd;
	
	@SerializedName("day")
	private String day;

	public Meeting(String building, String roomNumber, String timeStart,
			String timeEnd, String day) {
		super();
		this.building = building;
		this.roomNumber = roomNumber;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.day = day;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	
}
