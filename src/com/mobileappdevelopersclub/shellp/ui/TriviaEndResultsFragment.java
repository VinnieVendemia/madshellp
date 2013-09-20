package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;

import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.TakeTriviaQuiz;
import com.mobileappdevelopersclub.shellp.models.Questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TriviaEndResultsFragment extends Fragment{

	
	ArrayList < Questions > questions ;
	TextView result_text;


	public static TriviaEndResultsFragment newInstance() {
		TriviaEndResultsFragment fragment = new TriviaEndResultsFragment();
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
		View view = inflater.inflate(R.layout.trivia_results_list,  container, false);
		
		return view;


	}
	
}
