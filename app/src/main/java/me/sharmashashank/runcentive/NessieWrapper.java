package me.sharmashashank.runcentive;

import android.util.Log;
import com.reimaginebanking.api.java.models.*;

import com.reimaginebanking.api.java.NessieClient;
import com.reimaginebanking.api.java.NessieException;
import com.reimaginebanking.api.java.NessieResultsListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NessieWrapper {
    NessieClient nessieClient;
    private static final String TAG = NessieWrapper.class.getName();

    public NessieWrapper(String apiKey){
        nessieClient=NessieClient.getInstance();
        nessieClient.setAPIKey(apiKey);
        Log.d(TAG,apiKey);
        getAccounts();
    }

    public void getAccounts(){
        nessieClient.getAccounts(new NessieResultsListener() {
            @Override
            public void onSuccess(Object o, NessieException e) {
                if (e==null) {
                    ArrayList<Account> accounts = (ArrayList<Account>) o;
                    Log.d(TAG,accounts.get(0).toString());
                }
                else{
                    Log.d(TAG, "Cannot get all accounts" + e.getMessage());
                }
            }
        });
    }
}
