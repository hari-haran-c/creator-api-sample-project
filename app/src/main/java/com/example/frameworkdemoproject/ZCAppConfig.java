package com.example.frameworkdemoproject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ZCAppConfig {

    public static final String CONFIG_DOMAIN = "Domain";
    public static final String CONFIG_ACCOUNTS_DOMAIN = "Accounts_Domain";

    private static final Properties configProperties = new Properties();

    static void readPropertesFile(InputStream inputStream){
        try {
            configProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConfigValue(String configName, String defaultValue){
        return configProperties.getProperty(configName, defaultValue);
    }

}
