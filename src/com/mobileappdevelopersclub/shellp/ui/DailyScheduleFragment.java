package com.mobileappdevelopersclub.shellp.ui;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobileappdevelopersclub.shellp.Constants;
import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.models.Meeting;


public class DailyScheduleFragment extends Fragment{
	
	public final static String TAG = "DailyScheduleFragment";
	
	//constants
	private final String[] hours = {"8am" , "9am" , "10am" , "11am" , "12pm" , "1pm" , "2pm" , "3pm" , "4pm" , "5pm" 
										, "6pm" , "7pm" , "8pm" , "9pm" };
	
	int dayOfWeek;
	private String TODAY;
	View view;
	private Context mContext;
	private LinearLayout timeSlots;
	private FrameLayout classes;
	private HashMap<String, List<Meeting>> classToMeetingMap;  //Maps the name of a class to the list of meetings for the current day 
	private LayoutInflater mInflater;
	
	public static DailyScheduleFragment newInstance(int day, HashMap<String, List<Meeting>> classToMeetingMap) {
		DailyScheduleFragment frag = new DailyScheduleFragment();
		frag.dayOfWeek = day;
		frag.classToMeetingMap = classToMeetingMap;
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	
		TODAY = Constants.daysOfWeek[dayOfWeek];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		mInflater = inflater;
		view = inflater.inflate(R.layout.schedule_layout, null);
		timeSlots = (LinearLayout) view.findViewById(R.id.timeSlots);
		classes = (FrameLayout) view.findViewById(R.id.classes);
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
		

		for(String classKey : classToMeetingMap.keySet()) {
			addMeetingsToClassesView(classKey, classToMeetingMap.get(classKey));
		}
	}
	
	private void addMeetingsToClassesView(String className, List<Meeting> meetings) {
		
		for(int i=0; i < meetings.size(); i++) {
			Meeting currMeeting = meetings.get(i);
			LinearLayout classItemLayout =  (LinearLayout) mInflater.inflate(R.layout.schedule_list_item , null);
			int height = currMeeting.getClassLength();
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT , height);
			params.setMargins(0, currMeeting.getClassStartTime(), 0, 0);
			classItemLayout.setLayoutParams(params);
			addClassInfo(className, currMeeting, classItemLayout );
			classes.addView(classItemLayout);
		}
	
	}
	
	private void addClassInfo(String className, Meeting currMeeting, LinearLayout classItemLayout) { 
	
		((TextView)classItemLayout.findViewById(R.id.className)).setText(className);
		
		((TextView)classItemLayout.findViewById(R.id.classDate)).setText(currMeeting.getClassTimeLabel(TODAY));
		
		((TextView)classItemLayout.findViewById(R.id.classBuilding)).setText(currMeeting.getBuildingLabel());
	}
	
	
	private void addTimeSlots() {
		for(int i = 0; i < hours.length ; i++) {
			TextView time = new TextView(mContext);
			time.setText(hours[i]);
			time.setHeight(Constants.pixelsPerHour);
			timeSlots.addView(time);
		}
	}
}
