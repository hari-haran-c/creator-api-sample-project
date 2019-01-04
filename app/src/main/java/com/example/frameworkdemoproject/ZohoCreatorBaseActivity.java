package com.example.frameworkdemoproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class ZohoCreatorBaseActivity extends AppCompatActivity {
    public static Activity currentActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentActivity = this;
    }
}
