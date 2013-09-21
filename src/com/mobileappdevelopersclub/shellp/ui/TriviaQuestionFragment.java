package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.TakeTriviaQuiz;
import com.mobileappdevelopersclub.shellp.models.Questions;

public class TriviaQuestionFragment extends Fragment {

	public static final String TAG = "TakeTriviaQuiz";
	int testInt;
	ArrayList < Questions > questions ;
	ArrayList<Questions> answeredQuestions;
	TextView question;
	View view;

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
		answeredQuestions = ((TakeTriviaQuiz)getActivity()).answeredQuestions;


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.question_fragment,  container, false);


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
		//rBtn0.setChecked(false); // by default the first radio button is selected, This is un-selecting the radio button
		rBtn1.setText(answers[1]);
		rBtn2.setText(answers[2]);
		rBtn3.setText(answers[3]);
		RDG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup arg0, int id) {
				switch (id) {
				case -1:
					Log.v(TAG, "Choices cleared!");
					saveQuestionInfo();
					break;
				case R.id.radio0:
					Log.v(TAG, "Chose 0");
					saveQuestionInfo();
					break;
				case R.id.radio1:
					Log.v(TAG, "Chose 1");
					saveQuestionInfo();
					break;
				case R.id.radio2:
					Log.v(TAG, "Chose 2");
					saveQuestionInfo();
					break;
				case R.id.radio3:
					Log.v(TAG, "Chose 3");
					saveQuestionInfo();
					break;
				default:
					Log.v(TAG, "Chose none, should never reach here");
					break;
				}
			}
		});


		//((TextView)view.findViewById(R.id.textView1)).setText("Dylan is awesome, this is the new triv frag and it swipes really cool");
		return view;
	}



	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	/*@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

		int id =  RDG1.getCheckedRadioButtonId();

		// Manages answer when leaving the fragment
		if (id != -1){
			temp = (RadioButton) view.findViewById(id);

			if (temp.getText() != null && temp.getText().equals(questions.get(testInt -1).getCorrectAns())){
				questions.get(testInt -1).setAnswerdCorrectly(true);
			}else {
				questions.get(testInt -1).setAnswerdCorrectly(false);				
			}

			questions.get(testInt -1).setUserAnswer((String) temp.getText());
			questions.get(testInt -1).setIsAnswerd(true);				

		}

		Log.d(tag, questions.get(testInt -1).getUserAnswer());

	}	
	 */
	public void saveQuestionInfo(){

		int id = RDG1.getCheckedRadioButtonId();

		// Manages answer when leaving the fragment
		if (id != -1){
			temp = (RadioButton) view.findViewById(id);

			if (temp.getText() != null && temp.getText().equals(questions.get(testInt -1).getCorrectAns())){
				questions.get(testInt -1).setAnswerdCorrectly(true);
			}else {
				questions.get(testInt -1).setAnswerdCorrectly(false);				
			}

			questions.get(testInt -1).setUserAnswer((String) temp.getText());
			questions.get(testInt -1).setIsAnswerd(true);

			if(!answeredQuestions.contains(questions.get(testInt -1))) {
				Log.d(TAG, "adding to answered questions list");
				answeredQuestions.add(questions.get(testInt -1));
			} else {
				//question was previously added, possibly with another answer selected
				Log.d(TAG, "removing from answered list, then adding to answered questions list");
				answeredQuestions.remove(questions.get(testInt -1));
				answeredQuestions.add(questions.get(testInt -1));
			}

			if(answeredQuestions.size() == 10) {
				Log.d(TAG, "about to view results");
				viewResults();
			}

		}

		//		Log.d(tag, questions.get(testInt -1).getUserAnswer());

	}

	private void viewResults() {
		Log.d(TAG, "viewing results");
		Intent intent = new Intent(getActivity() , TriviaResultsActivity.class );
		Bundle b = new Bundle();
		ArrayList<Questions> bundleList = new ArrayList<Questions>();
		bundleList.addAll(questions);
		b.putParcelableArrayList("questions", bundleList);
		intent.putExtra("bundle", b);
		startActivity(intent);
		resetList();
	}
	
	private void resetList() {
		for(int i = 0; i < questions.size(); i++) {
			questions.get(i).setIsAnswerd(false);
		}
	}
}
