package me.sharmashashank.runcentive;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Security;
import java.util.Calendar;

import android.os.Handler;

public class running extends Activity implements LocationListener {

    boolean shouldStop = false;
    int timeStep = 5000;
    LocationManager mLocationManager;
    private final static String TAG = running.class.getName();
    Handler mHandler;
    TextView timerDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;

        mHandler = new Handler(Looper.getMainLooper());
        try {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        }
        catch(SecurityException ex){
            ex.printStackTrace();
        }
        //startClock(this);
        Log.d(TAG, "Inside onCreate");
        mHandler= new Handler(Looper.getMainLooper());
        timerDisplay = (TextView) findViewById(R.id.timerTextView);

        showTimer();


       // startClock(this);
    }

    public void showTimer(){
       final long startTimeTimer = System.currentTimeMillis();
       Handler mClockHander = new Handler();
        Runnable upDateTime = new Runnable(){
            public void run(){
                long timePassed = System.currentTimeMillis() - startTimeTimer;
                int seconds = (int) (timePassed/1000);
                int minutes = seconds/60;
                seconds %= 60;

                timerDisplay.setText(minutes +" : " + seconds);
            }
        };
        upDateTime.run();
        mClockHander.removeCallbacks(upDateTime);
        mClockHander.postDelayed(upDateTime,500);

    }


    private  LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
                Log.d("LocationListener", "NUJN");
                double    longitude = location.getLongitude();
                double    latitude = location.getLatitude();

            Toast.makeText(
                    getApplicationContext(), latitude +" "+ longitude,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_running, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG,"Location is null!!");
        Toast.makeText(getApplicationContext(),"Location changed" ,
                Toast.LENGTH_LONG).show();
        if (location != null) {
            Log.d(TAG, location.getLatitude() + " and " + location.getLongitude());
            Toast.makeText(getApplicationContext(),location.getLatitude() + " and " + location.getLongitude() ,
                    Toast.LENGTH_LONG).show();
        }
        if (shouldStop){
            try {
                mLocationManager.removeUpdates(this);
            }
            catch(SecurityException ex){
                Log.d(TAG,"Could not properly stop location manager");
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
