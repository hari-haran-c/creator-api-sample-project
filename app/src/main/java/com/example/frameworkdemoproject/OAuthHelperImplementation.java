package com.example.frameworkdemoproject;

import com.zoho.accounts.externalframework.ZohoSDK;
import com.zoho.creator.framework.exception.ZCException;
import com.zoho.creator.framework.interfaces.ZCOauthHelper;

class OAuthHelperImplementation implements ZCOauthHelper {

    @Override
    public String getAccessToken() throws ZCException {
        if(ZohoCreatorBaseActivity.currentActivity == null){
            return null;
        }
        return AuthorizationUtil.getToken(ZohoCreatorBaseActivity.currentActivity.getApplicationContext());
    }

    @Override
    public boolean isUserSignedIn() {
        if(ZohoCreatorBaseActivity.currentActivity == null){
            return false;
        }
        return ZohoSDK.getInstance(ZohoCreatorBaseActivity.currentActivity.getApplicationContext()).isUserSignedIn();
    }

    @Override
    public Object getUserData() {
        return null;
    }

    @Override
    public String getTransformedUrl(String s) {
        return s;
    }

    @Override
    public boolean checkAndLogout() {
        if(ZohoCreatorBaseActivity.currentActivity == null){
            return false;
        }
        return ZohoSDK.getInstance(ZohoCreatorBaseActivity.currentActivity.getApplicationContext()).checkAndLogout();
    }
}
