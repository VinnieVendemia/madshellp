package com.mobileappdevelopersclub.shellp;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mobileappdevelopersclub.shellp.ui.MainFragment;
import com.mobileappdevelopersclub.shellp.ui.ScheduleFragment;
import com.mobileappdevelopersclub.shellp.ui.WeatherFragment;
import com.mobileappdevelopersclub.ui.widgets.JazzyViewPager;
import com.mobileappdevelopersclub.ui.widgets.JazzyViewPager.TransitionEffect;

public class MainActivity extends ActionBarActivity {

	JazzyViewPager mJazzy;
	ShellpFragmentPagerAdapter mPagerAdapter;
	ActionBar actionBar;
//	private PullToRefreshAttacher mPullToRefreshAttacher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Globals.mgr = getSupportFragmentManager();
		Globals.actionBar = getSupportActionBar();
		actionBar = getSupportActionBar();
		mPagerAdapter = new ShellpFragmentPagerAdapter(getSupportFragmentManager());
		setupJazziness(TransitionEffect.Tablet);
//		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				mJazzy.setCurrentItem(tab.getPosition());
			}

			public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// hide the given tab
			}

			public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// probably ignore this event
			}
		};

		// Add 3 tabs, specifying the tab's text and TabListener
		for (int i = 0; i < 3; i++) {

			switch(i) {
			case 0:
				actionBar.addTab(actionBar.newTab().setText("Map").setTabListener(tabListener));
				break;
			case 1:
				actionBar.addTab(actionBar.newTab().setText("Schedule").setTabListener(tabListener));
				break;
			case 2: 
				actionBar.addTab(actionBar.newTab().setText("Weather").setTabListener(tabListener));
				break;
			default: 
				//Do Nothing, Should never reach here
				break;
			}

		}






	}

//	public PullToRefreshAttacher getPullToRefreshAttacher() {
//		return mPullToRefreshAttacher;
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem extrasItem = menu.findItem(R.id.action_extras);
//		extrasItem.setOnMenuItemClickListener(new OnMenuItemClickListener(){
//
//			@Override
//			public boolean onMenuItemClick(MenuItem arg0) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//			
//		});
//		
		return true;
	}


	public class ShellpFragmentPagerAdapter extends FragmentStatePagerAdapter {
		public ShellpFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment; 
			if(i == 0 ) {
				fragment = MapFragment.newInstance(i);
			} else if (i == 1){
				fragment = ScheduleFragment.newInstance();
			} else if (i == 2){
				fragment = WeatherFragment.newInstance(i);
			}  else {
				//Should never reach here, but safety check 
				fragment = MainFragment.newInstance(i);
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
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


}
