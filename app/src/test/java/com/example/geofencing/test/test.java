package com.example.geofencing.test; /**
 * Created by VenkataManiteja on 15-06-15.
 */

import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.example.geofencing.BuildConfig;
import com.example.geofencing.Settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
public class test {

     @Test
    public  void  myStringTest()

    {
        Settings mySet = new Settings();

        String expected_string = "Hello";
        String actual_string = mySet.my_StringReturn_method();

        assertEquals(expected_string,actual_string);


    }

}
