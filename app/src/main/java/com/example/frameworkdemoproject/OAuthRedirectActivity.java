package com.example.frameworkdemoproject;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zoho.accounts.externalframework.ZohoSDK;

public class OAuthRedirectActivity extends ZohoCreatorBaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ZohoSDK.getInstance(this).handleRedirection(this);
    }
}
