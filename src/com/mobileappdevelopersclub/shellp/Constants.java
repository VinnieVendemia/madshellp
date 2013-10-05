package com.mobileappdevelopersclub.shellp;

import android.content.Context;

public class Constants {

	public static final String MONDAY = "Monday";
	public static final String TUESDAY = "Tuesday";
	public static final String WEDNESDAY = "Wednesday";
	public static final String THURSDAY = "Thursday";
	public static final String FRIDAY = "Friday";
	
	
	public static final String[] daysOfWeek = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};
	
	public static int pixelsPerHour;
	public static int pixelsPerTenMin;
	public static int pixelsPerFifteenMin;
	private static final float DPS = 16.0f;  // Constant for calculating dp programaticaly 
	
	
	
	public static void initConstants(Context context) {
		final float scale = context.getResources().getDimension(R.dimen.one_hour_increment);
		
		pixelsPerHour = (int) (DPS * scale + 0.5f);
		pixelsPerTenMin = pixelsPerHour / 6;
		pixelsPerFifteenMin = pixelsPerHour / 4;
	}

}
