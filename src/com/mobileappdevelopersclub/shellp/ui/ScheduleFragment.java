package com.mobileappdevelopersclub.shellp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mobileappdevelopersclub.shellp.Globals;
import com.mobileappdevelopersclub.shellp.R;

public class ScheduleFragment extends Fragment implements OnItemSelectedListener {


	//Constants 
	public static final String MONDAY = "Monday";
	public static final String TUESDAY = "Tuesday";
	public static final String WEDNESDAY = "Wednesday";
	public static final String THURSDAY = "Thursday";
	public static final String FRIDAY = "Friday";
	public static final String[] daysOfWeek = {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};
	
	View view; 
	private Activity mActivity;
	ArrayAdapter<String> mSpinnerAdapter;
	private Spinner mSpinner;

	public static ScheduleFragment newInstance() {
		ScheduleFragment fragment = new ScheduleFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		setHasOptionsMenu(true);
		view = inflater.inflate(R.layout.schedulefragment_main, null);
		
		mSpinner = (Spinner) view.findViewById(R.id.daysOfWeek);
		mSpinnerAdapter = new ArrayAdapter<String>(Globals.actionBar.getThemedContext(),
				android.R.layout.simple_spinner_item, android.R.id.text1, daysOfWeek);
		mSpinner.setAdapter(mSpinnerAdapter);
		mSpinner.setOnItemSelectedListener(this);
		
		return view;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		 FragmentManager fm = getFragmentManager();
	     fm.beginTransaction().replace(R.id.schedule, DailyScheduleFragment.newInstance(position)).commit();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
