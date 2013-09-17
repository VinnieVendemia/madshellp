package com.mobileappdevelopersclub.shellp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileappdevelopersclub.shellp.R;

public class DailyScheduleFragment extends Fragment{
	
	private final int MONDAY = 0;
	private final int TUESDAY = 1;
	private final int WEDNESDAY = 2;
	private final int THURSDAY = 3;
	private final int FRIDAY = 4;
	
	int dayOfWeek;
	View view;
	
	public static DailyScheduleFragment newInstance(int day) {
		DailyScheduleFragment frag = new DailyScheduleFragment();
		frag.dayOfWeek = day;
		return frag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.schedule_layout, null);
		((TextView)view.findViewById(R.id.num)).setText(Integer.toString(dayOfWeek));
		return view;
		
	}
	
	

}
