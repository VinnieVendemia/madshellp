package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.R.id;
import com.mobileappdevelopersclub.shellp.R.layout;
import com.mobileappdevelopersclub.shellp.R.menu;
import com.mobileappdevelopersclub.shellp.adapters.TriviaGameAnswersAdapter;
import com.mobileappdevelopersclub.shellp.models.Questions;

public class TriviaResultsActivity extends Activity {

	private ListView mList;
	private TriviaGameAnswersAdapter questionsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_results_list);
		mList = (ListView) findViewById(R.id.results_list);
		
		
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("bundle");
		ArrayList<Questions> answeredQuestions = b.getParcelableArrayList("questions");
		
		questionsAdapter = new TriviaGameAnswersAdapter(this, 0, answeredQuestions);
		addHeader(answeredQuestions);
		mList.setAdapter(questionsAdapter);
		
		
	}
	
	private void addHeader(ArrayList<Questions> questions) {
		
		int numCorrect = 0;
		for(int i = 0; i < questions.size(); i++) {
			if(questions.get(i).isAnswerdCorrectly()) {
				numCorrect++;
			}
		}
		
		LinearLayout header = (LinearLayout) getLayoutInflater().inflate(R.layout.trivia_answers_header, null);
		((TextView)header.findViewById(R.id.num_correct)).setText(Integer.toString(numCorrect));
		
		mList.addHeaderView(header);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trivia_results, menu);
		return true;
	}

}
