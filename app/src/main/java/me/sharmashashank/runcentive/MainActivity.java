package me.sharmashashank.runcentive;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.reimaginebanking.api.java.NessieClient;

public class MainActivity extends Activity {

    public static String TAG="MainActivity";
    NessieClient nessieClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeNessieClient();
    }

    public void initializeNessieClient(){
        nessieClient = NessieClient.getInstance();
        nessieClient.setAPIKey(getApiKey());
    }

    private String getApiKey(){
        Context context=getApplicationContext();
        Bundle bundle=null;
        try {
             bundle = context.getPackageManager().
                    getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
        }
        catch(Exception ex){
            Log.d(TAG, "Cannot find the key");
        }
        return bundle.getString("capital_one_key", "");
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
}
