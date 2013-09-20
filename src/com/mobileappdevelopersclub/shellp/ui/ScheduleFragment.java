package com.mobileappdevelopersclub.shellp.ui;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobileappdevelopersclub.shellp.Globals;
import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.models.UMDClass;
import com.mobileappdevelopersclub.shellp.models.UMDClassResponse;
import com.mobileappdevelopersclub.shellp.transactions.AbsHttpTask;

public class ScheduleFragment extends Fragment implements OnItemSelectedListener {

	public static final String PREFS_NAME = "UserCASLogin";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "Password";
	public static final String NOT_SET = "Not Set";

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
	SharedPreferences userInfo;
	LayoutInflater inflater;

	public static ScheduleFragment newInstance() {
		ScheduleFragment fragment = new ScheduleFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
		userInfo = getActivity().getSharedPreferences(PREFS_NAME, 0);
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
					android.R.layout.simple_spinner_item, android.R.id.text1, daysOfWeek);
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

			UMDClassResponse weatherResponse = new Gson().fromJson(
					response, UMDClassResponse.class);
			onClassesFound(weatherResponse.getClasses());
		}

	}

	private void onClassesFound(List<UMDClass> classes) {
		//TODO: Add classes to the view
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
