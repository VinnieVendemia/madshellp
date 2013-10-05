package com.mobileappdevelopersclub.shellp.models;

import com.google.gson.annotations.SerializedName;
import com.mobileappdevelopersclub.shellp.Constants;

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

	private final int EIGHT_AM  = 800;
	
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
	
	public String getBuildingLabel() {
		StringBuilder sb = new StringBuilder();
		sb.append(building);
		sb.append(" ");
		sb.append(roomNumber);
		
		return sb.toString();
	}
	
	public String getClassTimeLabel(String day) {
		StringBuilder sb = new StringBuilder();
		sb.append(day);
		sb.append(" ");
		sb.append(getTimeFormat(timeStart));
		sb.append(" - ");
		sb.append(getTimeFormat(timeEnd));
		
		return sb.toString();
	}
	
	private String getTimeFormat(String time) {
		
		String newTime = "";
		
		if(time.length() > 3) {
			newTime = time.substring(0, 2) + ":" + time.substring(2);
		} else {
			newTime = time.substring(0, 1) + ":" + time.substring(1);
		}
		
		return newTime;
	}
	
	public int getClassLength() {
		int timeDifference = Integer.parseInt(timeEnd) - Integer.parseInt(timeStart);

		return convertTimeToPixels(timeDifference);
	}
	
	public int getClassStartTime() {
		int timeDifference = Integer.parseInt(timeStart) - EIGHT_AM;

		return convertTimeToPixels(timeDifference);
	}
	
	private int convertTimeToPixels(int timeDifference) {
		int timeInPixels = 0;
		
		while(timeDifference > 0) {
			if(timeDifference >= 60) {
				timeInPixels += Constants.pixelsPerHour;
				timeDifference -= 100;
			} else if(timeDifference % 15 == 0) {
				timeInPixels += Constants.pixelsPerFifteenMin;
				timeDifference -= 15;
			} else {
				timeInPixels += Constants.pixelsPerTenMin;
				timeDifference -= 10;
			}
		}
		
		return timeInPixels;
	}
	
}
