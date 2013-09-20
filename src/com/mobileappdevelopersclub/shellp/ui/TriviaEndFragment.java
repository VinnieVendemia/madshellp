package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.TakeTriviaQuiz;
import com.mobileappdevelopersclub.shellp.models.Questions;

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

		view.findViewById(R.id.getResults).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				checkIfAllQuestionsAnswered();
			}

		});

		//		result_text = (TextView) view.findViewById(R.id.TV1);
		//		result_text.setText("Here is how you did psh.... :");
		//		
		//		for (int i = 0; i < questions.size(); i++){
		//			result_text.append("Q:#"+i +" "+ questions.get(i).getQuestion()+ "\n");
		//			result_text.append("USR A:" +" "+ questions.get(i).getUserAnswer() + "\n");
		//			result_text.append("CRT A:" +" "+ questions.get(i).getCorrectAns() + "\n");
		//		}
		//		
		return view;


	}

	private void checkIfAllQuestionsAnswered() {
		boolean allQuestionsAnswered = true;

		for(int i = 0; i < questions.size(); i++) {
			allQuestionsAnswered = questions.get(i).isAnswerd();
		}

		if(allQuestionsAnswered) {
			FragmentManager fm = getFragmentManager();
			fm.beginTransaction().replace(R.id.results_layout, TriviaEndResultsFragment.newInstance()).commit();
		} else {
			Toast.makeText(getActivity(), "Not All Questions Answered", Toast.LENGTH_SHORT).show();
		}
	}
}
