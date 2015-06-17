package com.example.geofencing;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Date;

import com.example.geofencing.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.FloatMath;
import android.widget.TextView;

public class Scanning extends Activity implements LocationListener{
	
	LocationManager lm;
	Location l;
	String provider;
	TextView longitude,latitude,current_longitude,current_latitude,distance;
	
	SharedPreferences prefs; 
	
	private TimeStampDataSource datasource;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanning);
		
		
		longitude = (TextView)findViewById(R.id.longitude);
		latitude = (TextView)findViewById(R.id.latitude);
		
		current_longitude = (TextView)findViewById(R.id.current_longitude);
		current_latitude = (TextView)findViewById(R.id.current_latitude);
		
		
		distance  = (TextView)findViewById(R.id.distance);
		
		
		lm=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

		Criteria c=new Criteria();
		provider=lm.getBestProvider(c, false);
		l=lm.getLastKnownLocation(provider);
		if(l!=null)
		{
		     //get latitude and longitude of the location
		     double lng=l.getLongitude();
		     double lat=l.getLatitude();
		     //display on text view
		     current_longitude.setText(""+lng);
		     current_latitude.setText(""+lat);
		}
		else 
		{
			current_longitude.setText("Location unavailable");
			current_latitude.setText("Location unavailable");
		}
				
		//calculateDistance();
	}

	
	private void calculateDistance() {
	    
		
		prefs = getSharedPreferences(Util.SharedPrefKey, MODE_PRIVATE);
		String saved_longitude = prefs.getString(Util.LONGITUDE, "");
		longitude.setText(saved_longitude);
		String saved_latitude = prefs.getString(Util.LATITUDE, "");
		latitude.setText(saved_latitude);
		
		
		
		float lng_a = Float.parseFloat(current_longitude.getText().toString().trim());
		float lat_a = Float.parseFloat(current_latitude.getText().toString().trim());
		
		float lng_b= Float.parseFloat(saved_longitude);
		float lat_b= Float.parseFloat(saved_latitude);
		
		
		
		float pk = (float) (180/3.14169);

	    float a1 = lat_a / pk;
	    float a2 = lng_a / pk;
	    float b1 = lat_b / pk;
	    float b2 = lng_b / pk;

	    float t1 = FloatMath.cos(a1)*FloatMath.cos(a2)*FloatMath.cos(b1)*FloatMath.cos(b2);
	    float t2 = FloatMath.cos(a1)*FloatMath.sin(a2)*FloatMath.cos(b1)*FloatMath.sin(b2);
	    float t3 = FloatMath.sin(a1)*FloatMath.sin(b1);
	    double tt = Math.acos(t1 + t2 + t3);

	    double dis = 6366000*tt;
	    distance.setText(""+dis);
	    
	    double radius = Double.parseDouble(prefs.getString(Util.RADIUS, ""));
		
	    
	    if(radius>dis){
	    	
	    	//Storing them in database
	    	String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
	    	datasource = new TimeStampDataSource(this);
	        datasource.open();
	        datasource.createComment(currentDateTimeString);
	        datasource.close();
	        
	        try {
				setMobileDataEnabled(this, false);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	       

	        
	        
	    	new AlertDialog.Builder(this)
		    .setTitle("Holaa...")
		    .setMessage("You entered in a restricted zone..\nWe will turn off your Internet connection.")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();	
	    }
	    

	    
	    
	}
	
	
	private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
	    final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    final Class conmanClass = Class.forName(conman.getClass().getName());
	    final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
	    connectivityManagerField.setAccessible(true);
	    final Object connectivityManager = connectivityManagerField.get(conman);
	    final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
	    final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	    setMobileDataEnabledMethod.setAccessible(true);

	    setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
	    
	    WifiManager wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);
	}

	
	

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		double lng=l.getLongitude();
	    double lat=l.getLatitude();
	    current_longitude.setText(""+lng);
	    current_latitude.setText(""+lat);

		calculateDistance();
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
		calculateDistance();
	}
	
}
