package com.example.geofencing.test; /**
 * Created by VenkataManiteja on 15-06-16.
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import org.robolectric.Robolectric;
import android.content.Context;
import android.view.View;

import com.example.geofencing.Settings;
import static org.junit.Assert.assertEquals;

import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLocationManager;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)

public class locationtest  {
    private Settings mainActivity;

  /*  public void sendLocation() {
        try {
            double latitude=12.0; double longitude=12.0;
            Socket socket = new Socket("localhost", 5554); // usually 5554
            socket.setKeepAlive(true);
            String str = "geo fix " + longitude + " " + latitude ;
            Writer w = new OutputStreamWriter(socket.getOutputStream());
            System.out.println("string is " +str);
            w.write(str + "\r\n");
            w.flush();
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
  @Before
  public void setUp() {
      mainActivity = new Settings();
      mainActivity=Robolectric.buildActivity(Settings.class).create().get();
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

}
