package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mobileappdevelopersclub.shellp.Questions;
import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.TakeTriviaQuiz;

public class TriviaQuestionFragment extends Fragment {

	int testInt;
	ArrayList < Questions > questions ;
	TextView question;
	
	
	RadioButton rBtn0,rBtn1,rBtn2,rBtn3, temp;
	RadioGroup RDG1;
	Random rgen = new Random();  // Random number generator



	public static TriviaQuestionFragment newInstance(int testInt) {
		TriviaQuestionFragment fragment = new TriviaQuestionFragment();
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
		View view = inflater.inflate(R.layout.question_fragment,  container, false);


		//*************************************************
		// Trivia Game Content

		question = (TextView) view.findViewById(R.id.view);
		RDG1 = (RadioGroup) view.findViewById(R.id.radioGroup1);
		rBtn0 = (RadioButton) view.findViewById(R.id.radio0);
		rBtn1 = (RadioButton) view.findViewById(R.id.radio1);
		rBtn2 = (RadioButton) view.findViewById(R.id.radio2);
		rBtn3 = (RadioButton) view.findViewById(R.id.radio3);

		/*//Sets the font of the question
		display = (TextView) findViewById(R.id.display);

		submit= (Button) findViewById(R.id.button1);
		submit.setText("Submit");      
		RDG1 =(RadioGroup)findViewById(R.id.radioGroup1);

		rBtn0 = (RadioButton) findViewById(R.id.radio0);
		rBtn1 = (RadioButton) findViewById(R.id.radio1);
		rBtn2 = (RadioButton) findViewById(R.id.radio2);
		rBtn3 = (RadioButton) findViewById(R.id.radio3);

		RDG1.clearCheck() ; // clear the default selection of  first radio button in radio group
*/
		// Sets Question
		question.setText(questions.get(testInt -1 ).getQuestion());

		// Sets Answers
		String[] answers = questions.get(testInt-1).getAnswers();		
        rBtn0.setText(answers[0]);
        rBtn0.setChecked(false); // by default the first radio button is selected, This is un-selecting the radio button
        rBtn1.setText(answers[1]);
        rBtn2.setText(answers[2]);
        rBtn3.setText(answers[3]);

		

		//((TextView)view.findViewById(R.id.textView1)).setText("Dylan is awesome, this is the new triv frag and it swipes really cool");
		return view;
	}	
}
