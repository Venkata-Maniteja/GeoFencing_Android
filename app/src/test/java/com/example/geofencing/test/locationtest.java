package com.example.geofencing.test; /**
 * Created by VenkataManiteja on 15-06-16.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.hamcrest.CoreMatchers.*;
import android.location.Location;
import android.location.LocationManager;
import org.robolectric.Robolectric;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import com.example.geofencing.Settings;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.example.geofencing.R;

import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLocationManager;


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
        Location expectedLocation = location(locationManager.NETWORK_PROVIDER, 12.0, 20.0);

        shadowLocationManager.simulateLocation(expectedLocation);

      System.out.print("expected location is "+expectedLocation.getLatitude()+expectedLocation.getLongitude());
        Location actualLocation = mainActivity.latestLocation();
       System.out.print("actual location is "+actualLocation.getLatitude()+actualLocation.getLongitude());
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

    // Sanity check for the layout
    @Test
    public void shouldHaveButtonThatSays() throws Exception{
        // Verifies the button and text field exist
       // assertThat(btnGetCoords, notNullValue());
       // assertNotNull(latitudeTextField);
       // assertNotNull(longitueTextField);
        // Verifies the text of the button

    }

}
