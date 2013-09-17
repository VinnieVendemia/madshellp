package com.mobileappdevelopersclub.shellp.ui;

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
	int pixelsPerHour;
	
	public static DailyScheduleFragment newInstance(int day) {
		DailyScheduleFragment frag = new DailyScheduleFragment();
		frag.dayOfWeek = day;
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		final float scale = mContext.getResources().getDimension(R.dimen.one_hour_increment);
		pixelsPerHour = (int) (DPS * scale + 0.5f);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.schedule_layout, null);
		timeSlots = (LinearLayout) view.findViewById(R.id.timeSlots);
		classes = (LinearLayout) view.findViewById(R.id.classes);
		addTimeSlots();
		
		return view;
		
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
