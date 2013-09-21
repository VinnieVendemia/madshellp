package com.mobileappdevelopersclub.shellp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileappdevelopersclub.shellp.adapters.TriviaGameAnswersAdapter;
import com.mobileappdevelopersclub.shellp.models.Questions;
import com.mobileappdevelopersclub.shellp.ui.HomeTriviaFragment;
import com.mobileappdevelopersclub.shellp.ui.TriviaEndFragment;
import com.mobileappdevelopersclub.shellp.ui.TriviaQuestionFragment;
import com.mobileappdevelopersclub.shellp.ui.TriviaResultsActivity;
import com.mobileappdevelopersclub.ui.widgets.JazzyViewPager;
import com.mobileappdevelopersclub.ui.widgets.JazzyViewPager.TransitionEffect;

public class TakeTriviaQuiz extends ActionBarActivity{

	public final String TAG = "TakeTriviaQuiz";
	JazzyViewPager mJazzy;
	ShellpFragmentPagerAdapter mPagerAdapter;
	ActionBar actionBar;

	//**************************
	// Trivia Game Variables
	public ArrayList < Questions > list ;
	public ArrayList<Questions> answeredQuestions;
	public TriviaGameAnswersAdapter questionsAdapter;
	public ArrayList<Questions> questionsList;
	ArrayList <Questions> tempList, tempListCopy;
	private int count,correct;
	RadioButton rBtn0,rBtn1,rBtn2,rBtn3, temp;
	RadioGroup RDG1;
	TextView question,display;
	Button startQuiz;
	Activity currentActivity;

	int i = 0;
	Button submit;
	Random rgen = new Random();  // Random number generator
	MediaPlayer sound;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivia_home_fragment);

		//*************************************************
		// Fragment Navigation
		actionBar = getSupportActionBar();
		mPagerAdapter = new ShellpFragmentPagerAdapter(getSupportFragmentManager());
		setupJazziness(TransitionEffect.Tablet);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		answeredQuestions = new ArrayList<Questions>();
		
		try {
			questionsList = PlayWithRawFiles();
			list = new ArrayList<Questions>();
			for(int i = 0 ; i < 10; i++) {
				list.add(questionsList.get(i));
			}

			questionsAdapter = new TriviaGameAnswersAdapter(this, 0, answeredQuestions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Prints out questions in logcat
		/*
		for (int i = 0; i < list.size(); i++){
			Log.d(tag, list.get(i).getQuestion());


		}*/


		//**************************************************
		//
		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				mJazzy.setCurrentItem(tab.getPosition());
			}

			public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

			}

			public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// probably ignore this event
			}
		};

		// Add the number of tabs in the action bar
		for (int i = 0; i < 11; i++) {
			if (i == 0){
				actionBar.addTab(
						actionBar.newTab()
						.setText("Start")
						.setTabListener(tabListener));
			}  else {
				actionBar.addTab(
						actionBar.newTab()
						.setText("Question:  " + (i))
						.setTabListener(tabListener));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class ShellpFragmentPagerAdapter extends FragmentStatePagerAdapter {
		public ShellpFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override // Sets up each fragment, instantiates new class
		// HIERARCHY OF FRAGMENT VIEWS
		public Fragment getItem(int i) {
			Fragment fragment; 
			if (i == 0){
				fragment = HomeTriviaFragment.newInstance(i);
			} else {
				fragment = TriviaQuestionFragment.newInstance(i);
			}
			return fragment;
		}

		@Override // Number of tabs and fragments
		public int getCount() {
			return 11;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "OBJECT " + (position + 1);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			if(object != null){
				return ((Fragment)object).getView() == view;
			}else{
				return false;
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			Object obj = super.instantiateItem(container, position);
			mJazzy.setObjectForPosition(obj, position);
			return obj;
		}
	}

	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.pager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setAdapter(mPagerAdapter);
		mJazzy.setPageMargin(30);
		mJazzy.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {

					@SuppressLint("NewApi")
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the
						// corresponding tab.
						actionBar.setSelectedNavigationItem(position);
					}						
				});
	}



	/**********************************************************************************************
	 * This method parses input.txt and creates each question object.
	 * "input.txt" must be formatted correctly
	 *      Format must be the "question" separated by an "~"
	 *      then followed by the 4 options, with the correct answer last each separated with "~".
	 * ex:
	 * What color is the sky?~Green~Yellow~Purple~Blue
	 * The file must also not end with a "/n" (new-line character).
	 *
	 * This method will throw a IOException.
	 *
	 **********************************************************************************************/

	public ArrayList<Questions> PlayWithRawFiles() throws IOException {  
		ArrayList < Questions > list ; // might be able to move to class declaration/instantiation

		String  delims = "[~]";
		list = new ArrayList< Questions> () ;

		String str="";

		InputStream is = this.getResources().openRawResource(R.raw.trivia_questions);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		if (is!=null) {                                                
			while ((str = reader.readLine()) != null) {    

				String [] temp = str.split(delims);

				Questions newQuestion = new Questions (temp);
				list.add(newQuestion);
			}                              
		}              
		is.close();

		//randomize the list
		long seed = System.nanoTime();
		Collections.shuffle(list, new Random(seed));

		return list;
	}



	private void checkIfAllQuestionsAnswered() {
		Toast.makeText(this, "checking if all questions are answered", Toast.LENGTH_LONG).show();
		boolean allQuestionsAnswered = true;

		for(int i = 0; i < list.size(); i++) {
			Log.d(TAG, "Questions : " + Integer.toString(i) + "is answered: " + list.get(i).isAnswerd());
			allQuestionsAnswered = list.get(i).isAnswerd();
		}

		if(allQuestionsAnswered) {
			Intent intent = new Intent(this, TriviaResultsActivity.class );
			Bundle b = new Bundle();
			ArrayList<Questions> bundleList = new ArrayList<Questions>();
			bundleList.addAll(list);
			b.putParcelableArrayList("questions", bundleList);
			intent.putExtra("bundle", b);
			startActivity(intent);
			resetList();
		}
	}
	
	private void resetList() {
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setIsAnswerd(false);
		}
	}

}


