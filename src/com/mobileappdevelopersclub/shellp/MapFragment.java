package com.mobileappdevelopersclub.shellp;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mobileappdevelopersclub.shellp.transactions.GMapV2Direction;


public class MapFragment extends Fragment {
	
	static String TAG = "MapFragment";
	
	public static GoogleMap mMap;
	View view;
	private final double STAMP_LAT = 38.987568;
	private final double STAMP_LONG = -76.944457;
	private Context mContext;
	Location mUserLocation;
	
	public static MapFragment newInstance(int testInt) {
		MapFragment fragment = new MapFragment();
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		new GetUserLocation().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.map_fragment,  container, false);
		//((TextView)view.findViewById(R.id.textView1)).setText("Dylan is awesome, this is the new triv frag and it swipes really cool");

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
	
	private class GetDirections extends AsyncTask<Void, Void, Void> {
		
		PolylineOptions rectLine;
		
		@Override
		protected Void doInBackground(Void... params) {
			LatLng fromPosition = new LatLng(mUserLocation.getLatitude(), mUserLocation.getLongitude());
			LatLng toPosition = new LatLng(STAMP_LAT, STAMP_LONG);

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
			setDirections(rectLine);
		}
		
		
		
	}
	
	private void setDirections(PolylineOptions rectLine ) {
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
