package com.example.frameworkdemoproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.zoho.accounts.externalframework.ZohoErrorCodes;
import com.zoho.accounts.externalframework.ZohoSDK;
import com.zoho.accounts.externalframework.ZohoToken;
import com.zoho.accounts.externalframework.ZohoTokenCallback;
import com.zoho.creator.framework.exception.ZCException;
import com.zoho.creator.framework.user.ZOHOUser;

import java.util.ArrayList;

public class LoginActivity extends ZohoCreatorBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ProgressBar progressBar = findViewById(R.id.loginProgressBar);

        ZohoSDK.getInstance(getApplicationContext()).presentLoginScreen(this, new ZohoTokenCallback() {
            @Override
            public void onTokenFetchInitiated() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onTokenFetchComplete(ZohoToken zohoToken) {
                try {
                    ZOHOUser.setZOHOUser(new ZOHOUser("", "", new ArrayList<String>(1), "", ""));
                } catch (ZCException e) {
                    e.printStackTrace();
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                });
            }

            @Override
            public void onTokenFetchFailed(final ZohoErrorCodes zohoErrorCodes) {
                switch (zohoErrorCodes){
                    case user_cancelled:
                        finish();
                        break;
                }
            }
        }, null);
    }
}
