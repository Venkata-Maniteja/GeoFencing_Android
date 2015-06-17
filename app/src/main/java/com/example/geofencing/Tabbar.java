package com.example.geofencing;

import com.example.geofencing.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
public class Tabbar extends TabActivity implements OnTabChangeListener{
 
    /** Called when the activity is first created. */
      public static TabHost tabHost;
       
      
      private int PREVIOUS_SELECTED_TAB = 0;
      
      @Override
      public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.tabbar_activity);
           
          initLayout();    	 
       }
 
    private void initLayout() {
    	// Get TabHost Reference
        tabHost = getTabHost();
        tabHost.clearAllTabs();
        
        // Set TabChangeListener called when tab changed
     
        TabHost.TabSpec spec;
        Intent intent;
    
         /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Settings.class);
        spec = tabHost.newTabSpec("Settings")
      		  .setIndicator("Settings", getResources().getDrawable(R.drawable.ic_settings))
              .setContent(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(spec);
        
   
        /************* TAB2 ************/
        intent = new Intent().setClass(this, Scanning.class);
        spec = tabHost.newTabSpec("Scan")/*.setIndicator("Scan")*/
      		  		.setIndicator("Scan", getResources().getDrawable(R.drawable.ic_home))
                      .setContent(intent);  
        tabHost.addTab(spec);
   
        /************* TAB3 ************/
        intent = new Intent().setClass(this, History.class);
        spec = tabHost.newTabSpec("History")
      		  .setIndicator("History", getResources().getDrawable(R.drawable.ic_settings))
      		  .setContent(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        tabHost.addTab(spec);
     
        for(int ix = 0 ; ix < tabHost.getTabWidget().getChildCount();ix++){
      	  TextView x = (TextView) tabHost.getTabWidget().getChildAt(ix).findViewById(android.R.id.title);
          x.setTextSize(10);
        }		
	}

	@Override
    public void onTabChanged(String tabName) {
         
    	if (getTabHost().getCurrentTabTag().equals("Scan")) {
    			
    	}
    }
 
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	PREVIOUS_SELECTED_TAB = tabHost.getCurrentTab();
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	initLayout();
    	tabHost.setCurrentTab(PREVIOUS_SELECTED_TAB);
    }
    
    
}