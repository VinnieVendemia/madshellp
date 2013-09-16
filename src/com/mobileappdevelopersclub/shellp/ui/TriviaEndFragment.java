package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;

import com.mobileappdevelopersclub.shellp.Questions;
import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.TakeTriviaQuiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TriviaEndFragment extends Fragment {

	int testInt;
	ArrayList < Questions > questions ;
	TextView result_text;

	
	public static TriviaEndFragment newInstance(int testInt) {
		TriviaEndFragment fragment = new TriviaEndFragment();
		fragment.testInt = testInt;
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		questions = ((TakeTriviaQuiz)getActivity()).list;

		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.trivia_end_fragment,  container, false);

		result_text = (TextView) view.findViewById(R.id.TV1);
		result_text.setText("Here is how you did psh.... :");
		
		for (int i = 0; i < questions.size(); i++){
			result_text.append("Q:#"+i +" "+ questions.get(i).getQuestion()+ "\n");
			result_text.append("USR A:" +" "+ questions.get(i).getUserAnswer() + "\n");
			result_text.append("CRT A:" +" "+ questions.get(i).getCorrectAns() + "\n");
		}
		
		
		return view;
		
		
	}	
}
