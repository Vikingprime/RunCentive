package me.sharmashashank.runcentive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.reimaginebanking.api.java.models.Account;

public class MainActivity extends ActionBarActivity implements MoneyCallback {

    public static String TAG="MainActivity";
    public static NessieWrapper nessieWrapper;
    private Double checkingBalance=0.0;
    Button startRunButton;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this,
                R.xml.preferences,
                false);

        intent = getIntent();
        initializeNessieClient();
        nessieWrapper.getAccounts(this);
        startRunButton= (Button) findViewById(R.id.startrunbutton);
        startRunButton.setClickable(false);
//    RelativeLayout rl = (RelativeLayout) findViewById(R.id.testLayout);
//        rl.setBackground(getResources().getDrawable(R.drawable.road3));



    }

    @Override
    public void onBackPressed(){
        super.onDestroy();
    }

    public void initializeNessieClient(){
        nessieWrapper.ApiKey=getApiKey();
        nessieWrapper=nessieWrapper.getInstance();
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
        Intent intent = new Intent (this, SettingsActivity.class);
        startActivity(intent);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void buttonClickToRun(View view) {
        SharedPreferences   prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        double ratio = Double.parseDouble(prefs.getString(SettingsActivity.CAL_RATIO, ""+0.50));
        double weight =Double.parseDouble(prefs.getString(SettingsActivity.WEIGHT, ""+70));

        Intent intent = new Intent(this,running.class);
        intent.putExtra("Weight", weight);
        intent.putExtra("Ratio" , ratio);
        Log.d("Prefs", weight + " " +ratio);
        startActivity(intent);
    }

    @Override
    public void UpdateBalanceValues(Account checkingAccount, Account savingsAccuont) {
        checkingBalance=checkingAccount.getBalance();
        TextView textView = (TextView) findViewById(R.id.moneyBalance);
        double bal = checkingBalance;
        if (intent != null ){
            bal += intent.getDoubleExtra("Money",0);
        }
        String balance=String.format("%.2f", bal);
        textView.setText("$" + balance);
        startRunButton.setClickable(true);
    }

}
