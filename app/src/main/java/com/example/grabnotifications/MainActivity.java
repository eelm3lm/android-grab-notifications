package com.example.grabnotifications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;
import android.widget.TextView;
import android.os.StrictMode;


public class MainActivity extends AppCompatActivity {

    private ImageChangeBroadcastReceiver imageChangeBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        imageChangeBroadcastReceiver = new ImageChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.grabnotifications.notificationlistenerexample");
        registerReceiver(imageChangeBroadcastReceiver,intentFilter);
    }


    public class ImageChangeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView NewText = (TextView)findViewById(R.id.main_text);
            NewText.setText("Received");
        }
    }
}
