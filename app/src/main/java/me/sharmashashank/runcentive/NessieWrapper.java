package me.sharmashashank.runcentive;

import com.reimaginebanking.api.java.NessieClient;

public class NessieWrapper {
    NessieClient nessieClient;

    public NessieWrapper(String apiKey){
        nessieClient=NessieClient.getInstance();
        nessieClient.setAPIKey(apiKey);
    }
    
}
