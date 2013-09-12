package com.mobileappdevelopersclub.shellp.ui;

import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.TakeTriviaQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TriviaGameFrag extends Fragment {

	int testInt;
	Button start, settings;
	
	public static TriviaGameFrag newInstance(int testInt) {
		TriviaGameFrag fragment = new TriviaGameFrag();
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
		View view = inflater.inflate(R.layout.trivia_game_frag,  container, false);
		//start = (ImageButton) findViewById(R.id.imageButton1);
		
		((Button)view.findViewById(R.id.Bstart)).setOnClickListener(new OnClickListener(){
			
			public void onClick(View V) {
				Intent intent = new Intent(getActivity(), TakeTriviaQuiz.class);
				startActivity(intent);
			}
		});
		
		
		
		//((TextView)view.findViewById(R.id.textView1)).setText("Dylan is awesome, this is the new triv frag and it swipes really cool");
		return view;
	}	
}
