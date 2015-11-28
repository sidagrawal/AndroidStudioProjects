package com.phplogin.sidag.gcm;

import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.phplogin.sidag.data.ListProvider;

/**
 * Created by Siddhant on 11/13/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data){
        AccountManager accountManager = (AccountManager) getApplicationContext().getSystemService(ACCOUNT_SERVICE);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(accountManager.getAccountsByType("example.com")[0], ListProvider.getAuthority(), bundle);
    }
}