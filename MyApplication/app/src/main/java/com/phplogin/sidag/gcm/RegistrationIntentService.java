package com.phplogin.sidag.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by Siddhant on 11/14/2015.
 */
public class RegistrationIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String name = "RegistrationService";
    public RegistrationIntentService() {
        super(name);
    }
    public void onCreate(){
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken("939133146521",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d("token", "Token: " + token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendTokenToServer(String token){
    }
}
