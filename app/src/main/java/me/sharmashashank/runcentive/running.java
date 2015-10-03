package me.sharmashashank.runcentive;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;
import android.os.Handler;

public class running extends Activity implements LocationListener {

    boolean shouldStop=false;
    int timeStep=5000;
    LocationManager mLocationManager;
    private final static String TAG=running.class.getName();
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location=null;
        Log.d(TAG, "Inside onCreate");
        mHandler= new Handler(Looper.getMainLooper());
        startClock(this);
    }

    public void startClock(final running runobj){

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    while (!shouldStop) {
                        try {
                            mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, runobj, null);
                            final Location loc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (loc==null){
                                return;
                            }
                            Log.d(TAG, loc.toString());
                            mHandler.post(new Runnable() {
                                public void run() {
                                    Toast.makeText(
                                            getApplicationContext(), loc.toString(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        catch(SecurityException ex){
                            Log.d(TAG, "User did not enable GPS");
                        }
                        Thread.sleep(timeStep);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

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
