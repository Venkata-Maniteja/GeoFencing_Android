package com.example.geofencing.test; /**
 * Created by VenkataManiteja on 15-06-16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.geofencing.R;
import com.example.geofencing.Settings;
import com.example.geofencing.Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLocationManager;
import org.robolectric.shadows.ShadowPreferenceManager;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
      btnGetCoords = (Button) mainActivity.findViewById(R.id.getlocation);
      latitudeTextField = (EditText) mainActivity.findViewById(R.id.latitude);
      longitueTextField = (EditText) mainActivity.findViewById(R.id.longitude);

  }
    @After
    public void tearDown() throws Exception {
    }

  @Test
  public void shouldReturnTheLatestLocation() throws Exception {
        LocationManager locationManager = (LocationManager)
                Robolectric.application.getSystemService(Context.LOCATION_SERVICE);
        ShadowLocationManager shadowLocationManager = Robolectric.shadowOf(locationManager);
      Location expectedLocation = location(LocationManager.NETWORK_PROVIDER, 12.0, 20.0);

        shadowLocationManager.simulateLocation(expectedLocation);

//      System.out.print("expected location is " + expectedLocation.getLatitude() + expectedLocation.getLongitude());
        Location actualLocation = mainActivity.latestLocation();
//      System.out.print("actual location is " + actualLocation.getLatitude() + actualLocation.getLongitude());
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
    public void textFieldButtonExistCheck() throws Exception {


        // Verifies the button and text field exist
        assertThat(btnGetCoords, notNullValue());
        assertNotNull(latitudeTextField);
        assertNotNull(longitueTextField);
        assertThat((String) btnGetCoords.getText(), equalTo("Get Current Location"));
    }

    @Test
    public void textFieldNotNullCheck() throws Exception {


        assertNotNull(btnGetCoords);

        // Trigger a click on the button
        btnGetCoords.performClick();
        String result1 = latitudeTextField.getText().toString();
        String result2 = longitueTextField.getText().toString();
        assertThat(latitudeTextField, notNullValue());
        assertThat(longitueTextField, notNullValue());
        assertThat(result1, containsString("No"));  //textfiled is not empty after button click
        assertThat(result2, containsString("No"));
        System.out.print("The text is " + latitudeTextField.getText().toString());

    }

    @Test
    public void sharedPrefsTest() {

//        here im just checking whether shared prefs is accpeting data or not
        SharedPreferences sharedPreferences = ShadowPreferenceManager.getDefaultSharedPreferences(Robolectric.application.getApplicationContext());
        sharedPreferences.edit().putString(Util.LONGITUDE, "12345").commit();

        SharedPreferences sharedPreferences2 = Robolectric.application.getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        sharedPreferences2.edit().putString(Util.LONGITUDE, "12345").commit();
        mainActivity = Robolectric.buildActivity(Settings.class).create().get();
//        btnGetCoords.performClick();
        System.out.print("the lat text is " + latitudeTextField.getText().toString().trim());
    }

}
