package com.mobileappdevelopersclub.shellp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.mobileappdevelopersclub.shellp.R;

public class AboutUsActivity extends ActionBarActivity{

	private final String WEBSITE_URL = "http://mobileappdevelopersclub.com/";
	private final String TWITTER = "https://twitter.com/MADterps";
	private final String FACEBOOK = "https://www.facebook.com/groups/199304320170566/";
	private final String GOOGLE_PLUS = "https://plus.google.com/117982203877775374985/posts";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		
		findViewById(R.id.twitter).setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TWITTER));
				startActivity(browserIntent);
				return false;
			}
			
		});
		
		findViewById(R.id.facebook).setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK));
				startActivity(browserIntent);
				return false;
			}
			
		});
		
		findViewById(R.id.google_plus).setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLUS));
				startActivity(browserIntent);
				return false;
			}
			
		});
		
		findViewById(R.id.webpage).setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEBSITE_URL));
				startActivity(browserIntent);
				return false;
			}
			
		});
	}

	
	
	
}
