package com.example.geofencing.test; /**
 * Created by VenkataManiteja on 15-06-16.
 */

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.geofencing.R;
import com.example.geofencing.Settings;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLocationManager;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)

public class locationtest  {
    private Settings mainActivity;
    private Button btnGetCoords;
    private EditText latitudeTextField;
    private EditText longitueTextField;

  @Before
  public void setUp() {
      mainActivity = new Settings();
      mainActivity=Robolectric.buildActivity(Settings.class).create().get();

  }
    @After
    public void tearDown() throws Exception {
    }

  @Test
    public void shouldReturnTheLatestLocation() {
        LocationManager locationManager = (LocationManager)
                Robolectric.application.getSystemService(Context.LOCATION_SERVICE);
        ShadowLocationManager shadowLocationManager = Robolectric.shadowOf(locationManager);
      Location expectedLocation = location(LocationManager.NETWORK_PROVIDER, 12.0, 20.0);

        shadowLocationManager.simulateLocation(expectedLocation);

      System.out.print("expected location is " + expectedLocation.getLatitude() + expectedLocation.getLongitude());
        Location actualLocation = mainActivity.latestLocation();
      System.out.print("actual location is " + actualLocation.getLatitude() + actualLocation.getLongitude());
        assertEquals(expectedLocation, actualLocation);
    }

    private Location location(String provider, double latitude, double longitude) {
        Location location = new Location(provider);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setTime(System.currentTimeMillis());
        return location;
    }


    @Test
    public void textFieldCheck(){

        btnGetCoords = (Button) mainActivity.findViewById(R.id.getlocation);
        latitudeTextField = (EditText) mainActivity.findViewById(R.id.latitude);
        assertThat((String) btnGetCoords.getText(), equalTo("Get Current Location"));
    }



}
