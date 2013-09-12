package com.mobileappdevelopersclub.shellp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileappdevelopersclub.shellp.R;

public class MainFragment extends Fragment{
	
	int testInt;
	
	public static MainFragment newInstance(int testInt) {
		MainFragment fragment = new MainFragment();
		fragment.testInt = testInt;
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.main_fragment,  container, false);
		((TextView)view.findViewById(R.id.textView1)).setText(Integer.toString(testInt));
		return view;
	}
	
	

}
