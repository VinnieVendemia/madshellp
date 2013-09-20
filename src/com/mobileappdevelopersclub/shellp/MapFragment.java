package com.mobileappdevelopersclub.shellp;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mobileappdevelopersclub.shellp.transactions.GMapV2Direction;


public class MapFragment extends Fragment implements OnMarkerClickListener {

	//Constants 
	
	//used for debbuging purposes 
	static String TAG = "MapFragment";
	private final double STAMP_LAT = 38.987568;
	private final double STAMP_LONG = -76.944457;
	
	
	private final String CURRENT_LOC = "Current Location";
	private final String START_FLAG = "start";
	private final String ONE_FLAG = "one";
	private final String TWO_FLAG = "two";
	private final String THREE_FLAG = "three";
	
	
	public static GoogleMap mMap;
	View view;
	AutoCompleteTextView startLocationField;
	AutoCompleteTextView locationOneField;
	AutoCompleteTextView locationTwoField;
	AutoCompleteTextView locationThreeField;
	ImageView searchIcon;
	ImageView mStartMapPin;
	ImageView mEndMapPin;
	Drawable mRedMapPin;
	Drawable mYellowMapPin;
	Drawable mGreenMapPin;
	Drawable mBlueMapPin;
	LinearLayout mEnterLocationsLayout;
	FrameLayout mAddMoreLocations;
	private Context mContext;
	Location mUserLocation;
	ArrayAdapter<String> buildingNamesAdapter;
	//TODO: Change to <String,LatLng> map
	HashMap <String,String> buildingsMap;
	ImageView getUserLocIcon;
	LayoutInflater mInflater;
	int numOfExtraLocationFields = 0;


	public static MapFragment newInstance(int testInt) {
		MapFragment fragment = new MapFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();

		Resources res = getResources();
		//Must use an arraylist for the arrayadapter, otherwise will get unsupported operation exception
		ArrayList<String> namesAdapterArray = new ArrayList<String>();

		String[] buildingNames = res.getStringArray(R.array.bldg_names);
		String[] buildingLocations = res.getStringArray(R.array.bldg_location);        

		buildingsMap = new HashMap<String,String> ();

		for(int i = 0; i < buildingNames.length; i ++) {
			buildingsMap.put(buildingNames[i] , buildingLocations[i] );
			namesAdapterArray.add(buildingNames[i]);
		}

		buildingNamesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namesAdapterArray);

		createMapPins();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mInflater = inflater;
		view = mInflater.inflate(R.layout.map_fragment,  container, false);
		mEnterLocationsLayout = (LinearLayout) view.findViewById(R.id.enterSearchLocationsLayout); 
		mAddMoreLocations = (FrameLayout)view.findViewById(R.id.addMoreLocations);
		mStartMapPin = (ImageView)view.findViewById(R.id.startMapPin);
		mEndMapPin = (ImageView)view.findViewById(R.id.endMapPin);

		mStartMapPin.setImageDrawable(mBlueMapPin);
		mEndMapPin.setImageDrawable(mRedMapPin);

		startLocationField = (AutoCompleteTextView)view.findViewById(R.id.start_location);
		startLocationField.setAdapter(buildingNamesAdapter);
		locationOneField = (AutoCompleteTextView)view.findViewById(R.id.end_location);
		locationOneField.setAdapter(buildingNamesAdapter);

		searchIcon = (ImageView)view.findViewById(R.id.search_icon);
		searchIcon.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {

				// hide virtual keyboard
				InputMethodManager imm = 
						(InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchIcon.getWindowToken(), 0);

				String startName = startLocationField.getText().toString();
				String locationOneName = locationOneField.getText().toString();

				//long-lat of start
				String sLocation = buildingsMap.get(startName);
				//long-lat of end
				String locationOne = buildingsMap.get(locationOneName);

				if(sLocation != null && locationOne != null) {
					mMap.clear();
					new GetDirections().execute(startName, locationOneName, sLocation, locationOne, START_FLAG, ONE_FLAG);
				} else {
					Toast.makeText(getActivity(), "Could not perform search", Toast.LENGTH_SHORT).show();
				}
				
				checkLocationTwoField(locationOneName, locationOne);
				

				return false;
			}

		});

		getUserLocIcon = (ImageView)view.findViewById(R.id.getUserLocIcon);
		getUserLocIcon.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				new GetUserLocation().execute();
				Toast.makeText(getActivity(), "Getting you current location.", Toast.LENGTH_SHORT).show();
				return false;
			}

		});

		mAddMoreLocations.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				addAnotherLocationOption();
				return false;
			}

		});

		return view;
	}
	
	private void checkLocationTwoField(String locationName, String locationLatLng) {
		if(locationTwoField != null ) {
			String extraLocationName = locationTwoField.getText().toString();
			String extraLocationLatLng = buildingsMap.get(extraLocationName);
			if(extraLocationLatLng != null) {
				new GetDirections().execute(locationName, extraLocationName, locationLatLng, extraLocationLatLng, ONE_FLAG, TWO_FLAG);
				checkLocationThreeField(extraLocationName, extraLocationLatLng);
			}
		}
	}
	
	private void checkLocationThreeField(String locationName, String locationLatLng) {
		if(locationThreeField != null ) {
			String extraLocationName = locationThreeField.getText().toString();
			String extraLocationLatLng = buildingsMap.get(extraLocationName);
			if(extraLocationLatLng != null) {
				new GetDirections().execute(locationName, extraLocationName, locationLatLng, extraLocationLatLng, TWO_FLAG, THREE_FLAG);
			}
		}
	}

	private void addAnotherLocationOption() {
		final LinearLayout locationFieldTwoLayout = (LinearLayout) mInflater.inflate(R.layout.pin_with_text, null);
		final LinearLayout locationFieldThreeLayout = (LinearLayout) mInflater.inflate(R.layout.pin_with_text, null);
		if(numOfExtraLocationFields == 0) {
			
			((ImageView)locationFieldTwoLayout.findViewById(R.id.mapPin)).setImageDrawable(mGreenMapPin);
			locationTwoField = (AutoCompleteTextView)locationFieldTwoLayout.findViewById(R.id.location);
			locationTwoField.setAdapter(buildingNamesAdapter);
			mEnterLocationsLayout.addView(locationFieldTwoLayout);
			numOfExtraLocationFields++;
		} else if(numOfExtraLocationFields == 1) {
			((ImageView)locationFieldThreeLayout.findViewById(R.id.mapPin)).setImageDrawable(mYellowMapPin);
			locationThreeField = (AutoCompleteTextView)locationFieldThreeLayout.findViewById(R.id.location);
			locationThreeField.setAdapter(buildingNamesAdapter);
			mEnterLocationsLayout.addView(locationFieldThreeLayout);
			numOfExtraLocationFields++;
		} else {
			//do nothing, max of 2 extra location fields
			Toast.makeText(getActivity(), "Cannot Add Another Location, Max of 4 locations.", Toast.LENGTH_SHORT).show();
		}

	}

	private void createMapPins() {

		//make a blue pin
		mBlueMapPin = getResources().getDrawable( R.drawable.location_place_blue ); 
		ColorFilter blueFilter = new LightingColorFilter( Color.BLACK, Color.BLUE);
		mBlueMapPin.setColorFilter(blueFilter);

		//make a red pin 

		mRedMapPin = getResources().getDrawable( R.drawable.location_place_red ); 
		ColorFilter redFilter = new LightingColorFilter( Color.BLACK, Color.RED);
		mRedMapPin.setColorFilter(redFilter);

		//Make a green pin
		mGreenMapPin = getResources().getDrawable( R.drawable.location_place_green ); 
		ColorFilter greenFilter = new LightingColorFilter( Color.BLACK, Color.GREEN);
		mGreenMapPin.setColorFilter(greenFilter);

		//Make a yellow pin
		mYellowMapPin = getResources().getDrawable( R.drawable.location_place_yellow ); 
		ColorFilter yellowFilter = new LightingColorFilter( Color.BLACK, Color.YELLOW);
		mYellowMapPin.setColorFilter(yellowFilter);

	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setUpMapIfNeeded();
		new GetUserLocation().execute();
	}


	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// Do Not Miss this
		try {
			Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));  
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(fragment);
			ft.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.

		Log.d(TAG, "Starting setUpMapIfNeeded");
		//Markers not implemented yet
		//		if(markers == null) {
		//			markers = new ArrayList<Marker>();
		//		} else {
		//			markers.clear();
		//		}


		if (mMap == null) {
			mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			setUpMap();
		} else {
			mMap = ((SupportMapFragment) Globals.mgr.findFragmentById(R.id.map)).getMap();
			mMap.clear();
			setUpMap();
		}

	}

	private void setUpMap() {
		Log.d(TAG, "Setting up Map");
		//Code to set up a custom info window
		//			Globals.mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
		//
		//				// Use default InfoWindow frame
		//				@Override
		//				public View getInfoWindow(Marker arg0) {
		//					return null;
		//				}
		//
		//				// Defines the contents of the InfoWindow
		//				@Override
		//				public View getInfoContents(Marker arg0) {
		//
		//					// Getting view from the layout file info_window_layout
		//					View v = currentInflater.inflate(R.layout.windowlayout, null);
		//
		//					// Getting reference to the TextView to set latitude
		//					TextView title = (TextView) v.findViewById(R.id.title);
		//
		//					// Setting the latitude
		//					title.setText(arg0.getTitle());
		//
		//					// Returning the view containing InfoWindow contents
		//					return v;
		//
		//				}
		//			});
		//			}
		//		}

		//		CameraUpdate center = null;
		//		center= CameraUpdateFactory.newLatLng(new LatLng(STAMP_LAT, STAMP_LONG));
		//
		//		CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
		//
		//
		//		if (center != null) {
		//			mMap.moveCamera(center);
		//		}
		//
		//		mMap.animateCamera(zoom);

	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub


		marker.showInfoWindow();


		return false;
	}

	private void setUserLocationOnMap() {
		CameraUpdate center = null;
		center= CameraUpdateFactory.newLatLng(new LatLng(mUserLocation.getLatitude(),
				mUserLocation.getLongitude()));			

		CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);


		if (center != null) {
			mMap.moveCamera(center);
		}

		mMap.animateCamera(zoom);

		//Set User Location as 
		mMap.addMarker(new MarkerOptions().position(
				new LatLng(mUserLocation.getLatitude(), mUserLocation.getLongitude())).title("ME").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).showInfoWindow();


		addUserLocationToBuildingsMap();

	}

	private void addUserLocationToBuildingsMap() {

		StringBuilder sb = new StringBuilder();
		sb.append(Double.toString(mUserLocation.getLatitude()));
		sb.append(",");
		sb.append(Double.toString(mUserLocation.getLongitude()));

		String prevMapping = buildingsMap.put(CURRENT_LOC , sb.toString());
		if(prevMapping == null) {
			//There was no previous mapping for current loc, add to the list of search options
			buildingNamesAdapter.add(CURRENT_LOC);
			buildingNamesAdapter.notifyDataSetChanged();
		}

	}

	private class GetDirections extends AsyncTask<String, Void, Void> {

		PolylineOptions rectLine;
		String sName;
		String eName;
		LatLng startLocation;
		LatLng endLocation;
		
		String[] pinFlags = new String[2];
		@Override
		protected Void doInBackground(String... params) {
			sName = params[0];
			eName = params[1];
			String[] sLocation = params[2].split(",");
			String[] eLocation = params[3].split(",");
			pinFlags[0] = params[4];
			pinFlags[1] = params[5];
			startLocation = new LatLng(Double.parseDouble(sLocation[0]), Double.parseDouble(sLocation[1]) );
			endLocation = new LatLng(Double.parseDouble(eLocation[0]),  Double.parseDouble(eLocation[1]) );

			LatLng fromPosition = new LatLng(Double.parseDouble(sLocation[0])
					, Double.parseDouble(sLocation[1]));
			LatLng toPosition =	new LatLng(Double.parseDouble(eLocation[0])
					, Double.parseDouble(eLocation[1]));

			GMapV2Direction md = new GMapV2Direction();

			Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_WALKING);
			ArrayList<LatLng> directionPoint = md.getDirection(doc);
			rectLine = new PolylineOptions().width(7).color(Color.RED);

			for(int i = 0 ; i < directionPoint.size() ; i++) {          
				rectLine.add(directionPoint.get(i));
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setDirections(rectLine, sName, eName, startLocation, endLocation, pinFlags);
		}



	}

	private void setDirections(PolylineOptions rectLine, String start, String end, LatLng startLocation, LatLng endLocation, String[] pinFlags ) {
		mMap.addPolyline(rectLine);
		
		
		

		//add start marker
		if(!start.equals(CURRENT_LOC)) {
			//if !start is user loc, then add a marker at this pos
			String pinFlag = pinFlags[0];
			if(pinFlag.equals(START_FLAG)) {
				mMap.addMarker(new MarkerOptions().position(new LatLng(startLocation.latitude, startLocation.longitude))
						.title(start).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
			} else if (pinFlag.equals(ONE_FLAG)) {
				mMap.addMarker(new MarkerOptions().position(new LatLng(startLocation.latitude, startLocation.longitude))
						.title(start).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
			} else if(pinFlag.equals(TWO_FLAG)) {
				mMap.addMarker(new MarkerOptions().position(new LatLng(startLocation.latitude, startLocation.longitude))
						.title(start).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
			} else {
				mMap.addMarker(new MarkerOptions().position(new LatLng(startLocation.latitude, startLocation.longitude))
						.title(start).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
			}
		}

		//add end marker
		if(!end.equals(CURRENT_LOC)) {
			//if !end is user loc, then add a marker at this pos
			String pinFlag = pinFlags[1];
			if(pinFlag.equals(START_FLAG)) {
				mMap.addMarker(new MarkerOptions().position(new LatLng(endLocation.latitude, endLocation.longitude))
						.title(end).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
			} else if (pinFlag.equals(ONE_FLAG)) {
				mMap.addMarker(new MarkerOptions().position(new LatLng(endLocation.latitude, endLocation.longitude))
						.title(end).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
			} else if(pinFlag.equals(TWO_FLAG)) {
				mMap.addMarker(new MarkerOptions().position(new LatLng(endLocation.latitude, endLocation.longitude))
						.title(end).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
			} else {
				mMap.addMarker(new MarkerOptions().position(new LatLng(endLocation.latitude, endLocation.longitude))
						.title(end).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
			}
		}

		new GetUserLocation().execute();
	}


	private class GetUserLocation extends AsyncTask<Void,Void,Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			LocationManager locMgr = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
			mUserLocation = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setUserLocationOnMap();
		}



	}

}
