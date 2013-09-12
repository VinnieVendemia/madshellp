package com.mobileappdevelopersclub.shellp.ui;

import com.mobileappdevelopersclub.shellp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeTriviaFragment extends Fragment {

	int testInt;
	
	public static HomeTriviaFragment newInstance(int testInt) {
		HomeTriviaFragment fragment = new HomeTriviaFragment();
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
		View view = inflater.inflate(R.layout.trivia_home_fragment,  container, false);
		//((TextView)view.findViewById(R.id.textView1)).setText("Dylan is awesome, this is the new triv frag and it swipes really cool");
		return view;
	}	
}
