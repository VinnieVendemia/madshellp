package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.models.Meeting;
import com.mobileappdevelopersclub.shellp.models.UMDClass;


public class DailyScheduleFragment extends Fragment{
	
	//constants
	private final String[] hours = {"8am" , "9am" , "10am" , "11am" , "12pm" , "1pm" , "2pm" , "3pm" , "4pm" , "5pm" 
										, "6pm" , "7pm" , "8pm" , "9pm" };
	
	private static final float DPS = 16.0f;
	private final int MONDAY = 0;
	private final int TUESDAY = 1;
	private final int WEDNESDAY = 2;
	private final int THURSDAY = 3;
	private final int FRIDAY = 4;
	
	int dayOfWeek;
	View view;
	private ListView mList;
	private Context mContext;
	private LinearLayout timeSlots;
	private LinearLayout classes;
	private List<UMDClass> mClasses;
	private LayoutInflater mInflater;
	int pixelsPerHour;
	int pixelsPerTenMin;
	int pixelsPerFifteenMin;
	
	public static DailyScheduleFragment newInstance(int day, List<UMDClass> classes) {
		DailyScheduleFragment frag = new DailyScheduleFragment();
		frag.dayOfWeek = day;
		frag.mClasses = classes;
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		final float scale = mContext.getResources().getDimension(R.dimen.one_hour_increment);
		pixelsPerHour = (int) (DPS * scale + 0.5f);
		pixelsPerTenMin = pixelsPerHour / 6;
		pixelsPerFifteenMin = pixelsPerHour / 4;
		
//		generateTestClasses();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		mInflater = inflater;
		view = inflater.inflate(R.layout.schedule_layout, null);
		timeSlots = (LinearLayout) view.findViewById(R.id.timeSlots);
		classes = (LinearLayout) view.findViewById(R.id.classes);
		addTimeSlots();
		
		
		
		
		return view;
		
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		buildSchedule();
	}

	private void buildSchedule() {
		LinearLayout testClass =  (LinearLayout) mInflater.inflate(R.layout.schedule_list_item , null);
		int width = classes.getWidth();
		int height = pixelsPerHour  - pixelsPerTenMin;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , height);
		params.setMargins(0, 2 * pixelsPerHour, 0, 0);
		testClass.setLayoutParams(params);
		classes.addView(testClass);
	}
	
	private void generateTestClasses() {
		

		Meeting meeting = new Meeting("Build", "1122", "10:00",
					"10:50", "Monday");
		
		Meeting meeting1 = new Meeting("Build", "1122", "10:00",
				"10:50", "Wednesday");
		
		Meeting meeting2 = new Meeting("Build", "1122", "10:00",
				"10:50", "Friday");
		
		Meeting meeting3 = new Meeting("Build", "1122", "3:00",
				"3:15", "Monday");
	
		Meeting meeting4 = new Meeting("Build", "1122", "3:00",
			"3:15", "Wednesday");
	
		Meeting meeting5 = new Meeting("Build", "1122", "3:00",
			"3:15", "Friday");
		
		
		List<Meeting> meetings = new ArrayList<Meeting>();
		meetings.add(meeting);
		meetings.add(meeting1);
		meetings.add(meeting2);
		
		
		
		UMDClass classOne = new UMDClass("CMSC 411" , meetings);
		
		mClasses.add(classOne);
		
		
	}
	
	private void addTimeSlots() {
		for(int i = 0; i < hours.length ; i++) {
			TextView time = new TextView(mContext);
			time.setText(hours[i]);
			time.setHeight(pixelsPerHour);
			time.setBackgroundColor(getResources().getColor(R.color.cold_blue));
			timeSlots.addView(time);
		}
	}
}
