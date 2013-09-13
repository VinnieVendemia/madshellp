package com.mobileappdevelopersclub.shellp.ui;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mobileappdevelopersclub.shellp.MainActivity;
import com.mobileappdevelopersclub.shellp.R;
import com.mobileappdevelopersclub.shellp.adapters.WeatherListAdapter;
import com.mobileappdevelopersclub.shellp.models.Weather;
import com.mobileappdevelopersclub.shellp.models.WeatherResponse;
import com.mobileappdevelopersclub.shellp.transactions.AbsHttpTask;

public class WeatherFragment extends Fragment implements OnRefreshListener {

	int testInt;
	private final String URL = "http://api.worldweatheronline.com/free/v1/weather.ashx?q=20740&format=json&num_of_days=5&date=2013-09-05&key=jsrkcyrwcx4w2fjjsknwzg2c";
	ListView mList;
	WeatherListAdapter mAdapter;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	
	public static WeatherFragment newInstance(int testInt) {
		WeatherFragment fragment = new WeatherFragment();
		fragment.testInt = testInt;
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		fetchWeatherData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.weather_fragment,  container, false);
		mList = (ListView)view.findViewById(R.id.weather_list);
		mList.setVerticalFadingEdgeEnabled(true);

		mAdapter = new WeatherListAdapter(getActivity() , 0,  new ArrayList<Weather>());
		mList.setAdapter(mAdapter);
		mPullToRefreshAttacher = ((MainActivity) getActivity()).getPullToRefreshAttacher();
		mPullToRefreshAttacher.addRefreshableView(mList, this);
//		mList.setOnScrollListener(this);

		return view;
	}
	
	public void fetchWeatherData() {
		new FetchWeatherTask("GET", URL).execute();
	}

	private class FetchWeatherTask extends AbsHttpTask {

		public FetchWeatherTask(String verb, String url) {
			super(verb, url);
		}

		@Override
		protected void onError(String error) {
			// TODO Show dialog/message

		}

		@Override
		protected void onSuccess(String response) {

			WeatherResponse weatherResponse = new Gson().fromJson(
					response, WeatherResponse.class);
			onWeatherFound(weatherResponse.getData().getWeather());
		}

	}
	
	private void onWeatherFound(List<Weather> currWeather) {
		mAdapter.clear();
		mAdapter.addAll(currWeather);
		mAdapter.notifyDataSetChanged();
		mPullToRefreshAttacher.setRefreshComplete();
	}

	@Override
	public void onRefreshStarted(View view) {
		fetchWeatherData();
	}
}
