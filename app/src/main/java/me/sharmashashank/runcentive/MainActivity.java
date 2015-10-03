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
import android.widget.Button;
import android.widget.TextView;

import com.reimaginebanking.api.java.NessieClient;
import com.reimaginebanking.api.java.models.Account;

public class MainActivity extends Activity implements MoneyCallback {

    public static String TAG="MainActivity";
    public static NessieWrapper nessieWrapper;
    private Double checkingBalance=0.0;
    Button startRunButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeNessieClient();
        nessieWrapper.getAccounts(this);
        startRunButton= (Button) findViewById(R.id.startrunbutton);
        startRunButton.setClickable(false);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void buttonClickToRun(View view){
        Intent intent = new Intent(this,running.class);
        Bundle bundle=new Bundle();
        startActivity(intent);
    }

    @Override
    public void UpdateBalanceValues(Account checkingAccount, Account savingsAccuont) {
        checkingBalance=checkingAccount.getBalance();
        TextView textView = (TextView) findViewById(R.id.moneyBalance);
        textView.setText(checkingBalance.toString());
        startRunButton.setClickable(true);
    }
    public void mButtonIwillTakeOut(View view){
        Intent intent = new Intent(this,LocationUpdater.class);
        startActivity(intent);
    }
}
