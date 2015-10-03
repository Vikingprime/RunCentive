package me.sharmashashank.runcentive;

import com.reimaginebanking.api.java.models.Account;

/**
 * Created by Jesse on 10/3/2015.
 */
public interface MoneyCallback {
    public void UpdateBalanceValues(Account checkingAccount, Account savingsAccount);
}
