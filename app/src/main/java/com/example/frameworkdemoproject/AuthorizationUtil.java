package com.example.frameworkdemoproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.WorkerThread;

import com.zoho.accounts.externalframework.ZohoErrorCodes;
import com.zoho.accounts.externalframework.ZohoSDK;
import com.zoho.accounts.externalframework.ZohoToken;
import com.zoho.accounts.externalframework.ZohoTokenCallback;
import com.zoho.creator.framework.exception.ZCException;
import com.zoho.creator.framework.user.ZOHOUser;
import com.zoho.creator.framework.utils.ZOHOCreator;

public class AuthorizationUtil {

    static void init(Context context){
        ZohoSDK.getInstance(context).init(context.getString(R.string.zoho_client_id), context.getString(R.string.zoho_client_secret), ZOHOCreator.SCOPES, true);
    }

    @WorkerThread
    static String getToken(final Context context) {
        final Thread currentThread = Thread.currentThread();
        final String[] accessToken = new String[1];
        final Boolean[] isThreadShouldNotLock = new Boolean[1];
        isThreadShouldNotLock[0] = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                ZohoSDK.getInstance(context).getToken(new ZohoTokenCallback() {
                    @Override
                    public void onTokenFetchInitiated() {

                    }

                    @Override
                    public void onTokenFetchComplete(ZohoToken zohoToken) {
                        synchronized (currentThread) {
                            isThreadShouldNotLock[0] = true;
                            if(zohoToken != null) {
                                accessToken[0] = zohoToken.getToken();
                            }
                            currentThread.notify();
                        }
                    }

                    @Override
                    public void onTokenFetchFailed(ZohoErrorCodes zohoErrorCodes) {
                        synchronized (currentThread) {
                            isThreadShouldNotLock[0] = true;
                            currentThread.notify();
                        }
                    }
                });
            }
        }).start();

        synchronized (currentThread) {
            if(!isThreadShouldNotLock[0]) {
                try {
                    currentThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return accessToken[0];
    }

    @WorkerThread
    public static void logOutFromApp(final Activity activity){
        try {
            if(ZOHOCreator.getZohoUser(false) != null) {
                ZOHOCreator.getZohoUser(false).logout();
            }
            ZOHOUser.setUserCredentialNull();
        } catch (ZCException e) {
            e.printStackTrace();
        }
        ZohoSDK.getInstance(activity).revoke(new ZohoSDK.OnLogoutListener() {
            @Override
            public void onLogoutSuccess() {
                startAppFromLogin(activity);
            }

            @Override
            public void onLogoutFailed() {
                startAppFromLogin(activity);
            }
        });
    }

    private static void startAppFromLogin(Activity activity){
        Intent intent = new Intent(activity, LauncherActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
