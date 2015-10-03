package me.sharmashashank.runcentive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.reimaginebanking.api.java.NessieClient;

public class MainActivity extends Activity {

    public static String TAG="MainActivity";
    public static NessieWrapper nessieWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeNessieClient();
    }

    public void initializeNessieClient(){
        nessieWrapper=new NessieWrapper(getApiKey());
    }

    private String getApiKey(){
        Context context=getApplicationContext();
        Bundle bundle=null;
        return getResources().getString(R.string.capital_one_key);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void buttonClickToRun(View view){
        Intent intent = new Intent(this,running.class);

    }
}
