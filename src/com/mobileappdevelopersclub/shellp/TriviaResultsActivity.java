package com.mobileappdevelopersclub.shellp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TriviaResultsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_results_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trivia_results, menu);
		return true;
	}

}
