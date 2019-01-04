package com.example.frameworkdemoproject;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.zoho.creator.framework.utils.ZOHOCreator;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initializeCreatorSDK(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private static void initializeCreatorSDK(Context context) {
        ZOHOCreator.setOAuthHelper(new OAuthHelperImplementation());
        AuthorizationUtil.init(context);

        int resourceId = context.getResources().getIdentifier("zc_app_configuration", "raw", context.getPackageName());
        if (resourceId == 0) {
            throw new RuntimeException("zc_app_configuration.properties not found");
        }
        ZCAppConfig.readPropertesFile(context.getResources().openRawResource(resourceId));

        ZOHOCreator.setDefaultCreatorDomain(ZCAppConfig.getConfigValue(ZCAppConfig.CONFIG_DOMAIN, "creator.zoho.com"));
        ZOHOCreator.setPortalDomain(ZCAppConfig.getConfigValue(ZCAppConfig.CONFIG_DOMAIN, "creator.zoho.com"));

        if (ZCAppConfig.getConfigValue(ZCAppConfig.CONFIG_ACCOUNTS_DOMAIN, null) != null) {
            ZOHOCreator.setDefaultAccountsDomain(ZCAppConfig.getConfigValue(ZCAppConfig.CONFIG_ACCOUNTS_DOMAIN, null));
        } else if (ZOHOCreator.getCreatorServerDomain().equals("creator.zoho.com")) {
            ZOHOCreator.setDefaultAccountsDomain("accounts.zoho.com");
        } else if (ZOHOCreator.getCreatorServerDomain().equals("creator.zoho.eu")) {
            ZOHOCreator.setDefaultAccountsDomain("accounts.zoho.eu");
        } else if (ZOHOCreator.getCreatorServerDomain().equals("creator.zoho.in")) {
            ZOHOCreator.setDefaultAccountsDomain("accounts.zoho.in");
        } else if (ZOHOCreator.getCreatorServerDomain().equals("creator.zoho.com.cn")) {
            ZOHOCreator.setDefaultAccountsDomain("accounts.zoho.com.cn");
        }
        ZOHOCreator.initialize(context);
    }
}
