package com.mobileappdevelopersclub.shellp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.mobileappdevelopersclub.shellp.Questions;
import com.mobileappdevelopersclub.shellp.MainActivity.ShellpFragmentPagerAdapter;
import com.mobileappdevelopersclub.shellp.ui.AboutUsFragment;
import com.mobileappdevelopersclub.shellp.ui.HomeTriviaFragment;
import com.mobileappdevelopersclub.shellp.ui.MainFragment;
import com.mobileappdevelopersclub.shellp.ui.TriviaEndFragment;
import com.mobileappdevelopersclub.shellp.ui.TriviaGameFrag;
import com.mobileappdevelopersclub.shellp.ui.TriviaQuestionFragment;
import com.mobileappdevelopersclub.ui.widgets.JazzyViewPager;
import com.mobileappdevelopersclub.ui.widgets.JazzyViewPager.TransitionEffect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
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

public class TakeTriviaQuiz extends ActionBarActivity{
	
	public final String tag = "TakeTriviaQuiz";
	JazzyViewPager mJazzy;
	ShellpFragmentPagerAdapter mPagerAdapter;
	ActionBar actionBar;

	//**************************
	// Trivia Game Variables
	public ArrayList < Questions > list ;
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
		
		
		//*************************************************
		// Trivia Game Content
		
//		question = (TextView) findViewById(R.id.view);
//
//        //Sets the font of the question
//        question.setTypeface(font);
//        display = (TextView) findViewById(R.id.display);
//
//        submit= (Button) findViewById(R.id.button1);
//        submit.setText("Submit");      
//        RDG1 =(RadioGroup)findViewById(R.id.radioGroup1);
//
//        rBtn0 = (RadioButton) findViewById(R.id.radio0);
//        rBtn1 = (RadioButton) findViewById(R.id.radio1);
//        rBtn2 = (RadioButton) findViewById(R.id.radio2);
//        rBtn3 = (RadioButton) findViewById(R.id.radio3);
//
//        //Sets the font of the buttons
//        rBtn0.setTypeface(font);
//        rBtn1.setTypeface(font);
//        rBtn2.setTypeface(font);
//        rBtn3.setTypeface(font);
//
//        RDG1.clearCheck() ; // clear the default selection of  first radio button in radio group

		try {
			list = PlayWithRawFiles();
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
		for (int i = 0; i <= 11; i++) {
			if (i == 0){
				actionBar.addTab(
						actionBar.newTab()
						.setText("Start")
						.setTabListener(tabListener));
			} else if (i == 11){
				actionBar.addTab(
						actionBar.newTab()
						.setText("Results")
						.setTabListener(tabListener));
			} else {
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
			} else if (i == 11){
				fragment = TriviaEndFragment.newInstance(i);
			} else {
				fragment = TriviaQuestionFragment.newInstance(i);
			}
			return fragment;
		}

		@Override // Number of tabs and fragments
		public int getCount() {
			return 12;
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
            
            return list;
    }
    
}


