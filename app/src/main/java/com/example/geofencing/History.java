package com.example.geofencing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.geofencing.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class History extends ListActivity {

	private TimeStampDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		datasource = new TimeStampDataSource(this);
	    datasource.open();
	    
	    setListView();
	    
	    getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				
				final TimeStamp comment = datasource.getAllComments().get(position);
	            
				new AlertDialog.Builder(History.this)
			    .setTitle("Delete this item ?")
			    .setMessage("Are you sure you want to delete this item ?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	datasource.deleteComment(comment);;
			        	setListView();
			        }
			     })
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	dialog.dismiss();
			        }
			     })
			    
			     .setIcon(android.R.drawable.ic_dialog_alert)
			    .show();					
			}

			
		});

	}
	

	private void setListView() {
		// TODO Auto-generated method stub
		List<TimeStamp> values = datasource.getAllComments();

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<TimeStamp> adapter = new ArrayAdapter<TimeStamp>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		
		datasource.open();
		setListView();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		datasource.close();
		
	}

}
