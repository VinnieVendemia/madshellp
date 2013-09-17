package com.mobileappdevelopersclub.shellp;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mobileappdevelopersclub.shellp.transactions.GMapV2Direction;


public class MapFragment extends Fragment {
	
	//Constants 
	static String TAG = "MapFragment";
	private final double STAMP_LAT = 38.987568;
	private final double STAMP_LONG = -76.944457;
	
	public static GoogleMap mMap;
	View view;
	AutoCompleteTextView startLocation;
	AutoCompleteTextView endLocation;
	ImageView searchIcon;
	private Context mContext;
	Location mUserLocation;
	ArrayAdapter<String> buildingNamesAdapter;
	HashMap <String,String> buildingsMap;
	
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
        String[] buildingNames = res.getStringArray(R.array.bldg_names);
        String[] buildingLocations = res.getStringArray(R.array.bldg_location);        
        
        buildingsMap = new HashMap<String,String> ();
        
        for(int i = 0; i < buildingNames.length; i ++) {
        	buildingsMap.put(buildingNames[i] , buildingLocations[i] );
        }
        
        buildingNamesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line,buildingNames );
		
		
//		new GetUserLocation().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.map_fragment,  container, false);
		
		startLocation = (AutoCompleteTextView)view.findViewById(R.id.start_location);
		startLocation.setAdapter(buildingNamesAdapter);
		endLocation = (AutoCompleteTextView)view.findViewById(R.id.end_location);
		endLocation.setAdapter(buildingNamesAdapter);
	
		searchIcon = (ImageView)view.findViewById(R.id.search_icon);
		searchIcon.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				String startName = startLocation.getText().toString();
				String endName = endLocation.getText().toString();
				
				//long-lat of start
				String sLocation = buildingsMap.get(startName);
				//long-lat of end
				String eLocation = buildingsMap.get(endName);
				
				if(sLocation != null && eLocation != null) {
					//TODO: Perform search
					new GetDirections().execute(startName, endName, sLocation, eLocation);
				} else {
					Toast.makeText(getActivity(), "Could not perform search", Toast.LENGTH_SHORT).show();
				}
				
				return false;
			}
			
		});
		
		return view;
	}
	
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setUpMapIfNeeded();
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

		CameraUpdate center = null;
		center= CameraUpdateFactory.newLatLng(new LatLng(STAMP_LAT, STAMP_LONG));
		
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
		

		if (center != null) {
			mMap.moveCamera(center);
		}

		mMap.animateCamera(zoom);

	}
	
	private void setUserLocationOnMap() {
		CameraUpdate center = null;
		center= CameraUpdateFactory.newLatLng(new LatLng(mUserLocation.getLatitude(),
				mUserLocation.getLongitude()));			
		
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
		

		if (center != null) {
			mMap.moveCamera(center);
		}

		mMap.animateCamera(zoom);
		
		mMap.addMarker(new MarkerOptions().position(
				new LatLng(mUserLocation.getLatitude(), mUserLocation.getLongitude())).title("ME"));

		new GetDirections().execute();

	}
	
	private class GetDirections extends AsyncTask<String, Void, Void> {
		
		PolylineOptions rectLine;
		String sName;
		String eName;
		
		@Override
		protected Void doInBackground(String... params) {
			sName = params[0];
			eName = params[1];
			String[] sLocation = params[2].split(",");
			String[] eLocation = params[3].split(",");
			
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
			setDirections(rectLine, sName, eName);
		}
		
		
		
	}
	
	private void setDirections(PolylineOptions rectLine, String start, String end ) {
		mMap.addPolyline(rectLine);
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
