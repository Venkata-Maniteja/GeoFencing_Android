package com.example.geofencing;

import com.example.geofencing.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

//@RunWith(RobolectricTestRunner.class)


public class Settings extends Activity implements OnClickListener,LocationListener {

	
	EditText longitude,latitude,radius;
	Button getlocation,save;
	
	SharedPreferences prefs; 
	SharedPreferences.Editor editor ;
	
	
	LocationManager lm;
	Location l;
	String provider;

	//created this latest location for unit testing
	private Location latestLocation;


	@Override
	protected void onCreate(Bundle savedInstanceState) { //changed protected to public for testing
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	   //calling the location listener for testing
		initLocationListener();

		prefs = getSharedPreferences(Util.SharedPrefKey, MODE_PRIVATE);
		editor = prefs.edit();
		
		
		longitude = (EditText)findViewById(R.id.longitude);
		String saved_longitude = prefs.getString(Util.LONGITUDE, "");
		longitude.setText(saved_longitude);
		
		latitude = (EditText)findViewById(R.id.latitude);
		String saved_latitude = prefs.getString(Util.LATITUDE, "");
		latitude.setText(saved_latitude);
		
		radius  = (EditText)findViewById(R.id.radius);
		String saved_radius = prefs.getString(Util.RADIUS, "");
		radius.setText(saved_radius);
		
		
		
		
		getlocation = (Button)findViewById(R.id.getlocation);
		getlocation.setOnClickListener(this);
		save  = (Button)findViewById(R.id.save);
		save .setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() == getlocation.getId()){
			
			
			lm=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

			Criteria c=new Criteria();
			//criteria object will select best service based on
			//Accuracy, power consumption, response, bearing and monetary cost
			//set false to use best service otherwise it will select the default Sim network
			//and give the location based on sim network 
			//now it will first check satellite than Internet than Sim network location
			provider=lm.getBestProvider(c, false);
			//now you have best provider
			//get location
			l=lm.getLastKnownLocation(provider);
			if(l!=null)
			{
			     //get latitude and longitude of the location
			     double lng=l.getLongitude();
			     double lat=l.getLatitude();
			     //display on text view
			     longitude.setText(""+lng);
			     latitude.setText(""+lat);
			}
			else 
			{
				longitude.setText("No Provider");
				latitude.setText("No Provider");
			}

			

		}else if(v.getId() == save.getId()){
			
			if(longitude.getText().toString().trim().equals("")){
				longitude.setError("Longitude is null");
			}else if(latitude.getText().toString().trim().equals("")){
				latitude.setError("Latitude is null");
			}else if(radius.getText().toString().trim().equals("")){
				radius.setError("Radius is null");
			}else {
				editor.putString(Util.LONGITUDE, longitude.getText().toString().trim());
	        	editor.putString(Util.LATITUDE, latitude.getText().toString().trim());
	        	editor.putString(Util.RADIUS, radius.getText().toString().trim());
	        	editor.commit();
	        	
	        	Toast.makeText(this, "Values saved..", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}


	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latestLocation = location;
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}






//	my testing methods

//my method for unit testing ,this returns a string 'hello'
public String my_StringReturn_method(){

	return  "Hello";

}

//	testing method to return latitude and longitude as a float
	//this method will not work because it is called outside oncreate()
       /* public  double[] get_location(){

	   double testLon;
	   double testLAt;
	   lm=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
	   Criteria c=new Criteria();
	   provider =lm.getBestProvider(c, false);
	   l=lm.getLastKnownLocation(provider);
	   if(l!=null)
	   {
		   //get latitude and longitude of the location
		    testLon=l.getLongitude();
		    testLAt=l.getLatitude();

	   }
	   else
	   {
		   testLAt=0.0;
		   testLon=0.0;
	   }
	   return new double[]{testLAt,testLon};
   } */

	private void initLocationListener() {
		LocationManager locationManager = (LocationManager)
				getSystemService(Context.LOCATION_SERVICE);
		List<String> allProviders = locationManager.getAllProviders();
		for (String provider : allProviders) {
			locationManager.requestLocationUpdates(provider, 0, 0, this);
		}
	}

	public Location latestLocation() {

		lm=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

		Criteria c=new Criteria();
		//criteria object will select best service based on
		//Accuracy, power consumption, response, bearing and monetary cost
		//set false to use best service otherwise it will select the default Sim network
		//and give the location based on sim network
		//now it will first check satellite than Internet than Sim network location
		provider=lm.getBestProvider(c, false);
		//now you have best provider
		//get location
		l=lm.getLastKnownLocation(provider);

		return l;    //changed latestlocation to l for testing,this is the code from original method in onCreate()
	}






}
