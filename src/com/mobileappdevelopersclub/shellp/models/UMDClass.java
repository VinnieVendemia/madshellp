package com.mobileappdevelopersclub.shellp.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UMDClass {
	
	@SerializedName("Name")
	private String name;
	
	@SerializedName("Meetings")
	private List<Meeting> meetings;

	public UMDClass(String name, List<Meeting> meetings) {
		super();
		this.name = name;
		this.meetings = meetings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
	
}
