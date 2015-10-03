package me.sharmashashank.runcentive;

import android.util.Log;

import com.reimaginebanking.api.java.Constants.TransactionMedium;
import com.reimaginebanking.api.java.Constants.TransactionType;
import com.reimaginebanking.api.java.models.*;

import com.reimaginebanking.api.java.NessieClient;
import com.reimaginebanking.api.java.NessieException;
import com.reimaginebanking.api.java.NessieResultsListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class NessieWrapper {
    NessieClient nessieClient;
    private static final String TAG = NessieWrapper.class.getName();
    private static final String testCustomer="560f0205f8d8770df0ef9b3b";
    private static final String testSavingAccount="560f0206f8d8770df0efa2b1";
    private static final String testCheckingAccount="561035960aa60a0e00b71452";

    public static String ApiKey="";

    private Account savingsAccount;
    private Account checkingAccount;

    public static NessieWrapper getInstance(){
        if (mNessieWrapper==null){
            mNessieWrapper=new NessieWrapper();
        }
        return mNessieWrapper;
    }

    volatile static NessieWrapper mNessieWrapper=null;

    private NessieWrapper(){
        nessieClient=NessieClient.getInstance();
        nessieClient.setAPIKey(ApiKey);
        Log.d(TAG, ApiKey);
    }

    public void getAccounts(final MoneyCallback mCallback){
        nessieClient.getCustomerAccounts(testCustomer, new NessieResultsListener() {
            @Override
            public void onSuccess(Object o, NessieException e) {
                if (e == null) {
                    ArrayList<Account> accounts = (ArrayList<Account>) o;
                    for (Account acc : accounts) {
                        Log.d(TAG,acc.get_id() + " " + acc.getType() + " "+acc.getBalance());
                        if (acc.get_id().equals(testSavingAccount)) {
                            savingsAccount = acc;
                            Log.d(TAG,"Savings was found");
                        } else if (acc.get_id().equals(testCheckingAccount)) {
                            checkingAccount = acc;
                            Log.d(TAG,"Checking was found");
                        }
                    }

                    mCallback.UpdateBalanceValues(checkingAccount, savingsAccount);
                } else {
                    Log.d(TAG, "Did not get accounts");
                }


            }
        });

    }

    public void transferToChecking(double money){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String uniqueID = UUID.randomUUID().toString();
        //Transfer mTransfer=new Transfer("",dateFormat.format(date) ,"STATUS DONE", TransactionType.P2P, TransactionMedium.BALANCE, testSavingAccount, testCheckingAccount, money, "Runcentive transfer");
        Transfer mTransfer = new Transfer.Builder()
                .transaction_date(dateFormat.format(date))
                //.status("STATUS DONE")
                .amount(money)
                .payee_id(testCheckingAccount)
                .medium(TransactionMedium.BALANCE)
                .status("pending")
                .build();
        nessieClient.createTransfer(testSavingAccount, mTransfer, new NessieResultsListener() {
            @Override
            public void onSuccess(Object o, NessieException e) {
                if (e==null){
                    Log.d(TAG, "Successfully created transfer");
                }
                else{
                    Log.d(TAG, "Could not create transfer" + e.getMessage());
                }
            }
        });
    }

}
