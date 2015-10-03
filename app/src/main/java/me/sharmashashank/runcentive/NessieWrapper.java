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
    private static final String testCustomer="560f0205f8d8770df0ef9b3b";
    private static final String testSavingAccount="560f0206f8d8770df0efa2af";
    private static final String testCheckingAccount="560f0206f8d8770df0efa2b0";

    private Account savingsAccount;
    private Account checkingAccount;

    public NessieWrapper(String apiKey){
        nessieClient=NessieClient.getInstance();
        nessieClient.setAPIKey(apiKey);
        Log.d(TAG,apiKey);
        getAccounts();
    }

    public void getAccounts(){
        nessieClient.getCustomerAccounts(testCustomer, new NessieResultsListener() {
            @Override
            public void onSuccess(Object o, NessieException e) {
                if (e==null){
                    ArrayList<Account> accounts=(ArrayList<Account>) o;
                    for(Account acc: accounts){
                        if (acc.get_id().equals(testSavingAccount)){
                            savingsAccount=acc;
                        }
                        else if (acc.get_id().equals(testCheckingAccount)){
                            checkingAccount=acc;
                        }
                    }
                }
                else{
                    Log.d(TAG, "Did not get accounts");
                }
            }
        });
    }

}
