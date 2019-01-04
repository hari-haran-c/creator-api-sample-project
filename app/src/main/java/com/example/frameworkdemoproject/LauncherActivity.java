package com.example.frameworkdemoproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.zoho.accounts.externalframework.ZohoSDK;
import com.zoho.creator.framework.utils.ZOHOCreator;

public class LauncherActivity extends ZohoCreatorBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_launch_screen);

        final Button signInButton = findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZOHOCreator.setIsAppMemoryNotCleared(true);

                Class toOpenActivityClass;

                if (ZohoSDK.getInstance(LauncherActivity.this).isUserSignedIn()) {
                    toOpenActivityClass = MainActivity.class;
                } else {
                    toOpenActivityClass = LoginActivity.class;
                }
                Intent intent = new Intent(LauncherActivity.this, toOpenActivityClass);
                startActivity(intent);
                finish();
            }
        });
        if (ZohoSDK.getInstance(getApplicationContext()).isUserSignedIn()) {
            signInButton.setVisibility(View.GONE);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 500);
        }


    }
}
