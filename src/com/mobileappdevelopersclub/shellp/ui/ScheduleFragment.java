package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.mobileappdevelopersclub.shellp.Constants;
import com.mobileappdevelopersclub.shellp.Globals;
import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.models.Meeting;
import com.mobileappdevelopersclub.shellp.models.UMDClass;
import com.mobileappdevelopersclub.shellp.models.UMDClassResponse;
import com.mobileappdevelopersclub.shellp.transactions.AbsHttpTask;

public class ScheduleFragment extends Fragment implements OnItemSelectedListener {

	public static final String PREFS_NAME = "UserCASLogin";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "Password";
	public static final String NOT_SET = "Not Set";

	//Constants 

	View view; 
	private Activity mActivity;
	ArrayAdapter<String> mSpinnerAdapter;
	private Spinner mSpinner;
	SharedPreferences userInfo;
	LayoutInflater inflater;
	private List<UMDClass> mClasses;

	public static ScheduleFragment newInstance() {
		ScheduleFragment fragment = new ScheduleFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
		userInfo = getActivity().getSharedPreferences(PREFS_NAME, 0);
		generateTestClasses();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		this.inflater = inflater;
		//		Schedule Feature not yet implemented 
		//		if(userInfo.getString(USERNAME, NOT_SET).equals(NOT_SET) || userInfo.getString(PASSWORD, NOT_SET).equals(NOT_SET) ) {
		//			//This code should only run on 1st run of application 
		//
		//			view = inflater.inflate(R.layout.login_layout, null);
		//			final EditText username = (EditText) view.findViewById(R.id.email_login);
		//			final EditText passWord = (EditText) view.findViewById(R.id.password_login);
		//			view.findViewById(R.id.submit).setOnTouchListener(new OnTouchListener(){
		//
		//				@Override
		//				public boolean onTouch(View v, MotionEvent event) {
		//					String user = username.getText().toString();
		//					String pass = passWord.getText().toString();
		//
		//
		//					if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
		//						SharedPreferences userInfo = getActivity().getSharedPreferences(PREFS_NAME, 0);
		//						SharedPreferences.Editor editor = userInfo.edit();
		//						editor.putString(USERNAME, user);
		//						editor.putString(PASSWORD, pass);
		//
		//						editor.commit();
		//						
		//						testInflate();
		//
		//					} else {
		//						Toast.makeText(mActivity, "Username or Password Not entered", Toast.LENGTH_SHORT).show();
		//					}
		//					return true;
		//					
		//				}});
		//
		//		} else {
		setHasOptionsMenu(true);
		view = inflater.inflate(R.layout.schedulefragment_main, null);

		mSpinner = (Spinner) view.findViewById(R.id.daysOfWeek);
		mSpinnerAdapter = new ArrayAdapter<String>(Globals.actionBar.getThemedContext(),
				android.R.layout.simple_spinner_item, android.R.id.text1, Constants.daysOfWeek);
		mSpinner.setAdapter(mSpinnerAdapter);
		mSpinner.setOnItemSelectedListener(this);
		//			fetchClasses();
		//		}
		return view;
	}

	//	private void testInflate(){
	//		Toast.makeText(mActivity, "Trying to inflate", Toast.LENGTH_SHORT).show();
	//		setHasOptionsMenu(true);
	//		view = inflater.inflate(R.layout.schedulefragment_main, null);
	//
	//		mSpinner = (Spinner) view.findViewById(R.id.daysOfWeek);
	//		mSpinnerAdapter = new ArrayAdapter<String>(Globals.actionBar.getThemedContext(),
	//				android.R.layout.simple_spinner_item, android.R.id.text1, daysOfWeek);
	//		mSpinner.setAdapter(mSpinnerAdapter);
	//		mSpinner.setOnItemSelectedListener(this);
	//		view.refreshDrawableState();
	////		fetchClasses();
	//	}

	public void fetchClasses() {
		new FetchClassesTask("GET", UMDClassResponse.getUserUrl()).execute();
	}

	private class FetchClassesTask extends AbsHttpTask {

		public FetchClassesTask(String verb, String url) {
			super(verb, url);
		}

		@Override
		protected void onError(String error) {
			// TODO Show dialog/message

		}

		@Override
		protected void onSuccess(String response) {

			UMDClassResponse classResponse = new Gson().fromJson(
					response, UMDClassResponse.class);
			onClassesFound(classResponse.getClasses());
		}

	}

	private void onClassesFound(List<UMDClass> classes) {
		//TODO: Add classes to the view
		mClasses = classes;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		FragmentManager fm = getFragmentManager();
		HashMap<String, List<Meeting>> classToMeetingMap = getTodaysMap(position);		
		fm.beginTransaction().replace(R.id.schedule, DailyScheduleFragment.newInstance(position, classToMeetingMap)).commit();
	}

	private HashMap<String, List<Meeting>> getTodaysMap(int day) {
		HashMap<String, List<Meeting>> classToMeetingMap = new HashMap<String, List<Meeting>>();
		String today = Constants.daysOfWeek[day];

		for(int i=0; i < mClasses.size(); i++) {
			UMDClass curr = mClasses.get(i);
			classToMeetingMap.put(curr.getName(), curr.getTodaysMeetings(today));
		}
		
		return classToMeetingMap;
	}

	private void generateTestClasses() {

		//initialize mClasses for testing purposes 
		mClasses = new ArrayList<UMDClass>();
		
		//class one 
		Meeting meeting = new Meeting("Build", "1122", "1000",
				"1050", "Monday");

		Meeting meeting1 = new Meeting("Build", "1122", "1000",
				"1050", "Wednesday");

		Meeting meeting2 = new Meeting("Build", "1122", "1000",
				"1050", "Friday");

		Meeting meeting3 = new Meeting("Build", "1122", "1400",
				"1515", "Monday");

		Meeting meeting4 = new Meeting("Build", "1122", "1400",
				"1515", "Wednesday");

		Meeting meeting5 = new Meeting("Build", "1122", "1400",
				"1515", "Friday");

		//class two
		Meeting meeting6 = new Meeting("Build", "1122", "1100",
				"1150", "Friday");

		Meeting meeting7 = new Meeting("Build", "1122", "1100",
				"1150", "Monday");

		Meeting meeting8 = new Meeting("Build", "1122", "1100",
				"1150", "Wednesday");
		

		List<Meeting> meetings = new ArrayList<Meeting>();
		meetings.add(meeting);
		meetings.add(meeting1);
		meetings.add(meeting2);
		meetings.add(meeting3);
		meetings.add(meeting4);
		meetings.add(meeting5);

		UMDClass classOne = new UMDClass("CMSC 411" , meetings);
		
		List<Meeting> meetings2 = new ArrayList<Meeting>();
		meetings2.add(meeting6);
		meetings2.add(meeting7);
		meetings2.add(meeting8);

		UMDClass classTwo = new UMDClass("CMSC 417", meetings2);
		
		mClasses.add(classOne);
		mClasses.add(classTwo);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
